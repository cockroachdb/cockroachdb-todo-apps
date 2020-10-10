package com.cockroachdb.hacktoberfest.model;

import com.cockroachdb.hacktoberfest.model.enums.TaskStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
public class Task {
	long id;
	String title;
	String description;
	@Builder.Default
	TaskStatus status = TaskStatus.PENDING;
	@Builder.Default
	boolean archived = false;
	OffsetDateTime deadline;
	List<Comment> comments;
	
	@JsonIgnore
	long rowId;
}
