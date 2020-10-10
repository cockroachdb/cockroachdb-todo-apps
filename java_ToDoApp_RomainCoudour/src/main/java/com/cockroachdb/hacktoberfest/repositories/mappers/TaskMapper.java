/*
 *    Copyright 2020 - Romain Coudour
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.cockroachdb.hacktoberfest.repositories.mappers;

import com.cockroachdb.hacktoberfest.model.Task;
import com.cockroachdb.hacktoberfest.model.enums.TaskStatus;
import db.public_.tables.records.TaskRecord;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TaskMapper implements RecordMapper<TaskRecord, Task> {
	@Override
	public Task map(final TaskRecord record) {
		return Task.builder()
				.id(record.getId())
				.title(record.getTitle())
				.description(record.getDescription())
				.status(TaskStatus.valueOf(record.getStatus()))
				.deadline(record.getDeadline())
				.archived(record.getArchived())
				.comments(Collections.emptyList())
				.build();
	}
}
