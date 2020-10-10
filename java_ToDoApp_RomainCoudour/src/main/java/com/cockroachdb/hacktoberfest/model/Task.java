package com.cockroachdb.hacktoberfest.model;

import com.cockroachdb.hacktoberfest.model.enums.TaskStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
public class Task {
	String title;
	String description;
	TaskStatus status = TaskStatus.PENDING;
	boolean archived = false;
	OffsetDateTime deadline;
	List<Comment> comments;
}
