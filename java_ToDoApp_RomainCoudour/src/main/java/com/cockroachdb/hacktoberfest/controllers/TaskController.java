package com.cockroachdb.hacktoberfest.controllers;

import com.cockroachdb.hacktoberfest.model.Task;
import com.cockroachdb.hacktoberfest.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
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
		return null;
	}

	@GetMapping(path = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task getTask(@PathVariable("taskId") @NonNull final Long taskId) {
		return null;
	}

	@PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task create() {
		return null;
	}

	@PatchMapping(path = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task update(@PathVariable("taskId") @NonNull final Long taskId) {
		return null;
	}

	@DeleteMapping(path = "/{taskId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Task delete(@PathVariable("taskId") @NonNull final Long taskId) {
		return null;
	}

	@PutMapping(path = "/{taskId}/comments", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Task addComment(@PathVariable("taskId") @NonNull final Long taskId) {
		return null;
	}
}
