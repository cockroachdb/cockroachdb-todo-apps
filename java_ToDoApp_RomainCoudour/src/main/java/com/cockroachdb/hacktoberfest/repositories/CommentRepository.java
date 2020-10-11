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
package com.cockroachdb.hacktoberfest.repositories;

import com.cockroachdb.hacktoberfest.model.Comment;
import com.cockroachdb.hacktoberfest.model.Task;
import com.cockroachdb.hacktoberfest.model.dtos.CreateCommentDTO;
import com.cockroachdb.hacktoberfest.repositories.mappers.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

	private final DSLContext context;
	private final CommentMapper commentMapper;
	db.public_.tables.Comment COMMENT_TABLE = db.public_.tables.Comment.COMMENT;

	public Comment insert(final Task task, final CreateCommentDTO body) {
		return commentMapper.map(context.insertInto(COMMENT_TABLE)
				.set(COMMENT_TABLE.CONTENT, body.getContent())
				.set(COMMENT_TABLE.USERNAME, body.getUsername())
				.set(COMMENT_TABLE.TASK_ID, task.getRowId())
				.returning()
				.fetchOne());
	}

	public List<Comment> getAllByTask(final Task task) {
		return context.selectFrom(COMMENT_TABLE).where(COMMENT_TABLE.TASK_ID.eq(task.getRowId())).fetch(commentMapper);
	}
}
