package com.cockroachdb.hacktoberfest.services;

import com.cockroachdb.hacktoberfest.model.Task;
import com.cockroachdb.hacktoberfest.model.dtos.CreateTaskDTO;
import com.cockroachdb.hacktoberfest.model.dtos.UpdateTaskDTO;
import com.cockroachdb.hacktoberfest.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;

	public Task getByIdOrThrow(final long id) {
		return taskRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Resource 'TASK' not found with identifier (" + id + ")"));
	}

	public List<Task> getTasks(final Pageable pageable) {
		return taskRepository.getAllPaginated(pageable.getOffset(), pageable.getPageSize());
	}

	public Task create(final CreateTaskDTO body) {
		return taskRepository.insert(body);
	}

	public Task update(final Task task, final UpdateTaskDTO body) {
		return taskRepository.update(task, body);
	}

	public void delete(final Task task) {
		taskRepository.delete(task);
	}

	@Transactional
	public Task addComment(final Task task) {
		return null;
	}

}
