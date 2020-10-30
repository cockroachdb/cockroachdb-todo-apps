'''
Add the following header in a comment at the beginning of each source
file, and fill in your name and the year.

   Copyright [2020] [prantoran]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
'''


from datetime import datetime
from flask import Flask, request, flash, url_for, redirect, \
     render_template, abort, session
from flask_sqlalchemy import SQLAlchemy
import sqlalchemy.orm
from cockroachdb.sqlalchemy import run_transaction
# from flask.ext.session import Session

# SESSION_TYPE = 'memcache'

app = Flask(__name__)
# sess = Session()
app.config.from_pyfile('hello.cfg')
db = SQLAlchemy(app)
sessionmaker = sqlalchemy.orm.sessionmaker(db.engine)


class Todo(db.Model):
    __tablename__ = 'todos'
    id = db.Column('todo_id', db.Integer, primary_key=True)
    title = db.Column(db.String(60))
    text = db.Column(db.String)
    done = db.Column(db.Boolean)
    pub_date = db.Column(db.DateTime)

    def __init__(self, title, text):
        self.title = title
        self.text = text
        self.done = False
        self.pub_date = datetime.utcnow()


@app.route('/')
def show_all():
    def callback(session):
        return render_template(
            'show_all.html',
            todos=session.query(Todo).order_by(Todo.pub_date.desc()).all())
    return run_transaction(sessionmaker, callback)


@app.route('/new', methods=['GET', 'POST'])
def new():
    if request.method == 'POST':
        if not request.form['title']:
            flash('Title is required', 'error')
        elif not request.form['text']:
            flash('Text is required', 'error')
        else:
            def callback(session):
                todo = Todo(request.form['title'], request.form['text'])
                session.add(todo)
            run_transaction(sessionmaker, callback)
            flash(u'Todo item was successfully created')
            return redirect(url_for('show_all'))
    return render_template('new.html')


@app.route('/update', methods=['POST'])
def update_done():
    def callback(session):
        for todo in session.query(Todo).all():
            todo.done = ('done.%d' % todo.id) in request.form
    run_transaction(sessionmaker, callback)
    flash('Updated status')
    return redirect(url_for('show_all'))


if __name__ == '__main__':
    # app.config['SECRET_KEY'] = "<some key>" 
    app.secret_key = 'super secret key'
    app.config['SESSION_TYPE'] = 'filesystem'
    app.config['SESSION_PERMANENT']= False
    # sess.init_app(app)
    app.debug = True
    app.run()
