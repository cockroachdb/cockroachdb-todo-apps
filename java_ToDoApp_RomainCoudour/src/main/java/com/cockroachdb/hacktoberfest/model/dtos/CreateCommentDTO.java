package com.cockroachdb.hacktoberfest.model.dtos;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
public class CreateCommentDTO {
	@NonNull
	String content;
	@NonNull
	String username;
}
