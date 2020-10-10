package com.cockroachdb.hacktoberfest.services;

import com.cockroachdb.hacktoberfest.model.Comment;
import com.cockroachdb.hacktoberfest.model.Task;
import com.cockroachdb.hacktoberfest.model.dtos.CreateCommentDTO;
import com.cockroachdb.hacktoberfest.repositories.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

	public List<Comment> getAllByTask(final Task task) {
		return commentRepository.getAllByTask(task);
	}

	public Comment create(final Task task, final CreateCommentDTO body) {
		return commentRepository.insert(task, body);
	}

}
