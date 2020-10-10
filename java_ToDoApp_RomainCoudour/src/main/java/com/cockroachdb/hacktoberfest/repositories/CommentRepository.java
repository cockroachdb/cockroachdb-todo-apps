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
