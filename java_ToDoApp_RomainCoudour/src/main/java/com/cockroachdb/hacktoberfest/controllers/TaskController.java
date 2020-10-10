package com.cockroachdb.hacktoberfest.controllers;

import com.cockroachdb.hacktoberfest.model.Task;
import com.cockroachdb.hacktoberfest.model.dtos.AddCommentTaskDTO;
import com.cockroachdb.hacktoberfest.model.dtos.CreateTaskDTO;
import com.cockroachdb.hacktoberfest.model.dtos.UpdateTaskDTO;
import com.cockroachdb.hacktoberfest.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;

	@GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Task> getTasks(@RequestParam(value = "page", required = false, defaultValue = "0") final int page,
			@RequestParam(value = "limit", required = false, defaultValue = "10") final int limit) {
		return taskService.getTasks(PageRequest.of(page, limit));
	}

	@GetMapping(path = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task getTask(@PathVariable("taskId") @NonNull final Long taskId) {
		return taskService.getByIdOrThrow(taskId);

	}

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task create(@RequestBody @Validated @NonNull final CreateTaskDTO body) {
		return taskService.create(body);
	}

	@PatchMapping(path = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task update(@PathVariable("taskId") @NonNull final Long taskId, @RequestBody @Validated @NonNull final UpdateTaskDTO body) {
		final Task task = taskService.getByIdOrThrow(taskId);
		return taskService.update(task, body);
	}

	@DeleteMapping(path = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public String delete(@PathVariable("taskId") @NonNull final Long taskId) {
		final Task task = taskService.getByIdOrThrow(taskId);
		taskService.delete(task);
		return "Deletion successful";
	}

	@PutMapping(path = "/{taskId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task addComment(@PathVariable("taskId") @NonNull final Long taskId, @RequestBody @Validated @NonNull final AddCommentTaskDTO body) {
		final Task task = taskService.getByIdOrThrow(taskId);
		return taskService.addComment(task, body);
	}
}
