package com.cockroachdb.hacktoberfest.model.dtos;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
public class AddCommentTaskDTO {
	@NonNull
	String content;
	@NonNull
	String username;
}
