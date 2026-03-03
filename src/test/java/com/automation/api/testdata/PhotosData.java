package com.automation.api.testdata;

public class PhotosData {
    public static final int DEFAULT_PHOTO_ID = 1;
    public static final int INVALID_ID = 99999;
    public static final int DEFAULT_PHOTO_ALBUM_ID = 1;
    public static final String PHOTO_TITLE = "Test Photo Title";
    public static final String PHOTO_URL = "https://via.placeholder.com/600/92c952";
    public static final String PHOTO_THUMBNAIL_URL = "https://via.placeholder.com/150/92c952";

    // Updated photo test data
    public static final String UPDATED_PHOTO_TITLE = "Updated Photo Title";
    public static final String UPDATED_PHOTO_URL = "https://via.placeholder.com/600/771796";
    public static final String UPDATED_PHOTO_THUMBNAIL_URL = "https://via.placeholder.com/150/771796";

    // JSON schema paths
    private static final String SCHEMA_BASE_PATH = "schemas/";
    public static final String PHOTO_SCHEMA_PATH = SCHEMA_BASE_PATH + "photo-schema.json";
    public static final String PHOTO_CREATE_RESPONSE_SCHEMA_PATH = SCHEMA_BASE_PATH + "photo-create-response-schema.json";
}
