package com.cockroachdb.hacktoberfest.services;

import com.cockroachdb.hacktoberfest.model.Task;
import com.cockroachdb.hacktoberfest.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

	private final TaskRepository taskRepository;

	public Optional<Task> getById(final long id) {
		return taskRepository.findById(id);
	}

	public List<Task> getTasks(final SpringDataWebProperties.Pageable pageable) {
		return null;
	}

	@Transactional
	public Task create() {
		return null;
	}

	@Transactional
	public Task update() {
		return null;
	}

	@Transactional
	public void delete() {
	}

	@Transactional
	public Task addComment() {
		return null;
	}

}
