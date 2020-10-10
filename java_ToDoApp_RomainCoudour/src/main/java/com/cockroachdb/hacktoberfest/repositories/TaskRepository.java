package com.cockroachdb.hacktoberfest.repositories;

import com.cockroachdb.hacktoberfest.model.Task;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

	private final DSLContext context;

	public Optional<Task> findById(final long id) {
		return null;
	}
}
