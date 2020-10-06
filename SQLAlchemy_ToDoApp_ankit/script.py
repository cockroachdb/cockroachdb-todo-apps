from datetime import datetime
import random
from sqlalchemy import create_engine, Column, Integer, String
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from cockroachdb.sqlalchemy import run_transaction

Base = declarative_base()
secure_cluster = True           
connect_args = {}

if secure_cluster:
    connect_args = {
        'sslmode': 'require',
        'sslrootcert': 'certs/ca.crt',
        'sslkey': 'certs/client.maxroach.key',
        'sslcert': 'certs/client.maxroach.crt'
    }
else:
    connect_args = {'sslmode': 'disable'}

engine = create_engine(
    'cockroachdb://maxroach@localhost:26257/todo',
    connect_args=connect_args,
    echo=True                   
)
Base.metadata.create_all(engine)


#Todo model
class ToDo(Base):
    __tablename__ = 'todo'
    id = Column(Integer,primary_key=True)
    content = Column(String(200),nullable=False)


run_transaction(sessionmaker(bind=engine),
                lambda s: create_todo(s, 5))

def create_todo(sess,n):
    all_todos = []
    idx = iter(range(n))
    for i in idx:
        todo_content = "Hello World"
        idx_id = random.randint(1, n)
        all_todos.append(
            ToDo(
                id = idx_id,
                content = todo_content
            )
        )
    sess.add_all(all_todos)

def get_todo_by_id():
    id = random.randint(1, 5)
    return id