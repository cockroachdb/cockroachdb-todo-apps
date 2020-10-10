package com.cockroachdb.hacktoberfest.repositories;

import com.cockroachdb.hacktoberfest.model.Task;
import com.cockroachdb.hacktoberfest.model.dtos.CreateTaskDTO;
import com.cockroachdb.hacktoberfest.model.dtos.UpdateTaskDTO;
import com.cockroachdb.hacktoberfest.repositories.mappers.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TaskRepository {

	private final DSLContext context;
	private final TaskMapper taskMapper;
	db.public_.tables.Task TASK_TABLE = db.public_.tables.Task.TASK;

	public Optional<Task> findById(final long id) {
		return context.selectFrom(TASK_TABLE).where(TASK_TABLE.ID.eq(id)).fetchOptional(taskMapper);
	}

	public List<Task> getAllPaginated(final long offset, final int limit) {
		return context.selectFrom(TASK_TABLE).offset(offset).limit(limit).fetch(taskMapper);
	}

	public Task insert(final CreateTaskDTO body) {
		return taskMapper.map(context.insertInto(TASK_TABLE)
				.set(TASK_TABLE.TITLE, body.getTitle())
				.set(TASK_TABLE.DESCRIPTION, body.getDescription())
				.set(TASK_TABLE.DEADLINE, body.getDeadline())
				.returning()
				.fetchOne());
	}

	public Task update(final Task task, final UpdateTaskDTO body) {
		final Map<String, Object> map = new HashMap<>();
		if (body.getDeadline() != null) {
			map.put(TASK_TABLE.DEADLINE.getName(), body.getDeadline());
		}
		if (body.getDescription() != null) {
			map.put(TASK_TABLE.DESCRIPTION.getName(), body.getDescription());
		}
		if (body.getStatus() != null) {
			map.put(TASK_TABLE.STATUS.getName(), body.getStatus().name());
		}
		if (body.getTitle() != null) {
			map.put(TASK_TABLE.TITLE.getName(), body.getTitle());
		}
		if (body.getArchived() != null) {
			map.put(TASK_TABLE.ARCHIVED.getName(), body.getArchived());
		}
		return taskMapper.map(context.update(TASK_TABLE).set(map)
				.where(TASK_TABLE.ID.equal(task.getId()))
				.returning()
				.fetchOne());
	}

	public void delete(final Task task) {
		context.delete(TASK_TABLE).where(TASK_TABLE.ID.eq(task.getId())).execute();
	}

}
