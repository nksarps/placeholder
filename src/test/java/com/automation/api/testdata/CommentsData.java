package com.automation.api.testdata;

public class CommentsData {
	public static final int DEFAULT_COMMENT_ID = 1;
	public static final int INVALID_ID = 99999;

	public static final int DEFAULT_COMMENT_POST_ID = 1;
	public static final String COMMENT_NAME = "Test Comment Author";
	public static final String COMMENT_EMAIL = "test@example.com";
	public static final String COMMENT_BODY = "This is a test comment created by automation";

	// Updated comment test data
	public static final String UPDATED_COMMENT_NAME = "Updated Comment Author";
	public static final String UPDATED_COMMENT_EMAIL = "updated@example.com";
	public static final String UPDATED_COMMENT_BODY = "This is an updated test comment";

	// JSON schema paths
	private static final String SCHEMA_BASE_PATH = "schemas/";
	public static final String COMMENT_SCHEMA_PATH = SCHEMA_BASE_PATH + "comment-schema.json";
	public static final String COMMENT_CREATE_RESPONSE_SCHEMA_PATH = SCHEMA_BASE_PATH + "comment-create-response-schema.json";
}
