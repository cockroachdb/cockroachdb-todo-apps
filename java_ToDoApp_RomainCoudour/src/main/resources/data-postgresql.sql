-- CLEAR SCHEMA
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS task CASCADE;

-- CREATE TABLES
CREATE TABLE task (
	id SERIAL NOT NULL,
	description text,
	status text DEFAULT 'PENDING' NOT NULL,
	archived boolean DEFAULT false NOT NULL,
	deadline timestamp (4) with time zone NOT NULL
);

CREATE TABLE comment (
	id SERIAL NOT NULL,
    task_id bigint NOT NULL,
	content text NOT NULL,
	username text NOT NULL,
	CONSTRAINT fk_task FOREIGN KEY (task_id) references task ON DELETE CASCADE
);