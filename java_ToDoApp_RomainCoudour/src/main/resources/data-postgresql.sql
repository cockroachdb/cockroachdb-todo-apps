-- CLEAR SCHEMA
DROP TABLE IF EXISTS task CASCADE;
DROP SEQUENCE IF EXISTS task_ids;
DROP SEQUENCE IF EXISTS comment_ids;

-- CREATE IDs SEQUENCES
CREATE SEQUENCE task_ids;
CREATE SEQUENCE comment_ids;

-- CREATE TABLES
CREATE TABLE task (
	id bigint DEFAULT nextval('task_ids') NOT NULL,
	description text,
	status text DEFAULT 'PENDING' NOT NULL,
	archived boolean DEFAULT false NOT NULL,
	deadline timestamp (4) with time zone NOT NULL,
	CONSTRAINT pk_task PRIMARY KEY (id)
);

CREATE TABLE comment (
	id bigint DEFAULT nextval('comment_ids') NOT NULL,
    task_id bigint NOT NULL,
	content text NOT NULL,
	username text NOT NULL,
	CONSTRAINT pk_comment PRIMARY KEY (id),
	CONSTRAINT fk_task FOREIGN KEY (task_id) references task ON DELETE CASCADE
);

-- POPULATE
INSERT INTO task (id, description, deadline)
VALUES (1, 'helloworld', '2020-10-10 08:28:23.319000');
INSERT INTO comment (id, content, username, task_id)
VALUES (1, 'hello you!', 'world', 1);