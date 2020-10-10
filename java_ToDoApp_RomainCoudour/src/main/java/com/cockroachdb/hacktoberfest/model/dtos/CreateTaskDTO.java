package com.cockroachdb.hacktoberfest.model.dtos;

import lombok.Value;
import org.springframework.lang.NonNull;

import java.time.OffsetDateTime;

@Value
public class CreateTaskDTO {
	@NonNull
	String title;
	@NonNull
	String description;
	@NonNull
	OffsetDateTime deadline = OffsetDateTime.now().plusWeeks(1);
}
