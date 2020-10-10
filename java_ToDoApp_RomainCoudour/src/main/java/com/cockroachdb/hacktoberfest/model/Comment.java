package com.cockroachdb.hacktoberfest.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment {
	Long id;
	String content;
	String username;
}
