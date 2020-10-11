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

import com.cockroachdb.hacktoberfest.model.Comment;
import db.public_.tables.records.CommentRecord;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper implements RecordMapper<CommentRecord, Comment> {
	@Override
	public Comment map(final CommentRecord record) {
		return Comment.builder()
				.id(record.getId())
				.content(record.getContent())
				.username(record.getUsername())
				.build();
	}
}
