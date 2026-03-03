package com.automation.api.testdata;


public class AlbumsData {
    public static final int DEFAULT_ALBUM_ID = 1;
    public static final int INVALID_ID = 99999;

    public static final String ALBUM_TITLE = "Test Album Title";

    // Updated album test data
    public static final String UPDATED_ALBUM_TITLE = "Updated Album Title";

    // JSON schema paths
    private static final String SCHEMA_BASE_PATH = "schemas/";
    public static final String ALBUM_SCHEMA_PATH = SCHEMA_BASE_PATH + "album-schema.json";
    public static final String ALBUM_CREATE_RESPONSE_SCHEMA_PATH = SCHEMA_BASE_PATH + "album-create-response-schema.json";
}
