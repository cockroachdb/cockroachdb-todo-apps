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
