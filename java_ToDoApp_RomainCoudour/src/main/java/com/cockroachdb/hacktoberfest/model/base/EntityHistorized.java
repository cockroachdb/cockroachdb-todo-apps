package com.cockroachdb.hacktoberfest.model.base;

import java.time.OffsetDateTime;

public abstract  class EntityHistorized {
	OffsetDateTime createdAt;
	OffsetDateTime updatedAt;
}
