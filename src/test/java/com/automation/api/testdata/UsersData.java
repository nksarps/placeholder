package com.automation.api.testdata;

public class UsersData {
    public static final int DEFAULT_USER_ID = 1;
    public static final int INVALID_ID = 99999;

    public static final String USER_NAME = "testuser";
    public static final String USER_EMAIL = "testuser@example.com";
    public static final String USER_PHONE = "1234567890";
    public static final String USER_WEBSITE = "https://example.com";

    // Updated user test data
    public static final String UPDATED_USER_NAME = "updateduser";
    public static final String UPDATED_USER_EMAIL = "updateduser@example.com";
    public static final String UPDATED_USER_PHONE = "0987654321";
    public static final String UPDATED_USER_WEBSITE = "https://updated.example.com";

    // JSON schema paths
    private static final String SCHEMA_BASE_PATH = "schemas/";
    public static final String USER_SCHEMA_PATH = SCHEMA_BASE_PATH + "user-schema.json";
    public static final String USER_CREATE_RESPONSE_SCHEMA_PATH = SCHEMA_BASE_PATH + "user-create-response-schema.json";
}
