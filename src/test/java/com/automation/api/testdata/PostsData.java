package com.automation.api.testdata;

public class PostsData {
    public static final int DEFAULT_POST_ID = 1;
    public static final int INVALID_ID = 99999;

    public static final int POST_USER_ID = 1;
    public static final String POST_TITLE = "Test Post Title";
    public static final String POST_BODY = "This is a test post body created by automation";

    // Updated post test data
    public static final String UPDATED_POST_TITLE = "Updated Post Title";
    public static final String UPDATED_POST_BODY = "This is the updated post body";

    // JSON schema paths
    private static final String SCHEMA_BASE_PATH = "schemas/";
    public static final String POST_SCHEMA_PATH = SCHEMA_BASE_PATH + "post-schema.json";
    public static final String POST_CREATE_RESPONSE_SCHEMA_PATH = SCHEMA_BASE_PATH + "post-create-response-schema.json";
}
