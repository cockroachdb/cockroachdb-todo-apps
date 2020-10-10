package com.cockroachdb.hacktoberfest.model.dtos;

import com.cockroachdb.hacktoberfest.model.enums.TaskStatus;
import lombok.Value;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;

@Value
public class UpdateTaskDTO {
	@Nullable
	String title;
	@Nullable
	String description;
	@Nullable
	TaskStatus status;
	@Nullable
	Boolean archived;
	@Nullable
	OffsetDateTime deadline;
}
