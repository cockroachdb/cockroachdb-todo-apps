package com.cockroachdb.hacktoberfest.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Comment {
	Long id;
	String content;
	String username;
}
