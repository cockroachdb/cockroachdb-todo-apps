CREATE SEQUENCE todo_id_seq
  start 1
  increment 1;

CREATE TABLE todo
(
    id int NOT NULL DEFAULT nextval('todo_id_seq') primary key,
    data varchar(255),
    created_on timestamp with time zone
)