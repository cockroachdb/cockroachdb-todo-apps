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
import com.cockroachdb.hacktoberfest.model.PageRequest;
import com.cockroachdb.hacktoberfest.model.Task;
import com.cockroachdb.hacktoberfest.model.dtos.CreateCommentDTO;
import com.cockroachdb.hacktoberfest.model.dtos.CreateTaskDTO;
import com.cockroachdb.hacktoberfest.model.dtos.UpdateTaskDTO;
import com.cockroachdb.hacktoberfest.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;
	private final CommentService commentService;

	public Task getByIdOrThrow(final long id) {
		return computeWithComment(taskRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Resource 'TASK' not found with identifier (" + id + ")")));
	}

	public List<Task> getTasks(final PageRequest pageRequest) {
		return taskRepository.getAllPaginated(pageRequest)
				.stream()
				.map(this::computeWithComment)
				.collect(Collectors.toList());
	}

	public Task create(final CreateTaskDTO body) {
		return taskRepository.insert(body);
	}

	public Task update(final Task task, final UpdateTaskDTO body) {
		final Task updatedTask = taskRepository.update(task, body);
		return computeWithComment(updatedTask);
	}

	public void delete(final Task task) {
		taskRepository.delete(task);
	}

	@Transactional
	public Task addComment(final Task task, final CreateCommentDTO body) {
		commentService.create(task, body);
		return computeWithComment(task);
	}

	private Task computeWithComment(final Task task) {
		final List<Comment> comments = commentService.getAllByTask(task);
		task.setComments(comments);
		return task;
	}

}
