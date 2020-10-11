--
--  Copyright 2020 - Romain Coudour
--
--  Licensed under the Apache License, Version 2.0 (the "License");
--  you may not use this file except in compliance with the License.
--  You may obtain a copy of the License at
--
--      http://www.apache.org/licenses/LICENSE-2.0
--
--  Unless required by applicable law or agreed to in writing, software
--  distributed under the License is distributed on an "AS IS" BASIS,
--  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--  See the License for the specific language governing permissions and
--  limitations under the License.
--
-- CLEAR SCHEMA
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS task CASCADE;

-- CREATE TABLES
CREATE TABLE task (
	id SERIAL NOT NULL,
	title text NOT NULL,
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