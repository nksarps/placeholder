package com.automation.api.utils;

/**
 * Centralized test data (IDs and other reusable parameters) for API tests.
 */
public final class TestData {
    private TestData() {}

    public static final int DEFAULT_USER_ID = 1;
    public static final int DEFAULT_POST_ID = 1;
    public static final int DEFAULT_COMMENT_POST_ID = 1;
    public static final int DEFAULT_PHOTO_ALBUM_ID = 1;
    public static final int DEFAULT_TODO_USER_ID = 1;

    public static final int INVALID_ID = 99999;

    // POST test data
    public static final String POST_TITLE = "Test Post Title";
    public static final String POST_BODY = "This is a test post body created by automation";
    public static final int POST_USER_ID = 1;

    // PUT test data
    public static final String UPDATED_POST_TITLE = "Updated Post Title";
    public static final String UPDATED_POST_BODY = "This is the updated post body";

    // Comment test data
    public static final int DEFAULT_COMMENT_ID = 1;
    public static final String COMMENT_NAME = "Test Comment Author";
    public static final String COMMENT_EMAIL = "test@example.com";
    public static final String COMMENT_BODY = "This is a test comment created by automation";

    // Updated comment test data
    public static final String UPDATED_COMMENT_NAME = "Updated Comment Author";
    public static final String UPDATED_COMMENT_EMAIL = "updated@example.com";
    public static final String UPDATED_COMMENT_BODY = "This is an updated test comment";

    // Album test data
    public static final int DEFAULT_ALBUM_ID = 1;
    public static final String ALBUM_TITLE = "Test Album Title";

    // Updated album test data
    public static final String UPDATED_ALBUM_TITLE = "Updated Album Title";

    // Photo test data
    public static final int DEFAULT_PHOTO_ID = 1;
    public static final String PHOTO_TITLE = "Test Photo Title";
    public static final String PHOTO_URL = "https://via.placeholder.com/600/92c952";
    public static final String PHOTO_THUMBNAIL_URL = "https://via.placeholder.com/150/92c952";

    // Updated photo test data
    public static final String UPDATED_PHOTO_TITLE = "Updated Photo Title";
    public static final String UPDATED_PHOTO_URL = "https://via.placeholder.com/600/771796";
    public static final String UPDATED_PHOTO_THUMBNAIL_URL = "https://via.placeholder.com/150/771796";

    // Todo test data
    public static final int DEFAULT_TODO_ID = 1;
    public static final String TODO_TITLE = "Test Todo Title";
    public static final boolean TODO_COMPLETED = false;

    // Updated todo test data
    public static final String UPDATED_TODO_TITLE = "Updated Todo Title";
    public static final boolean UPDATED_TODO_COMPLETED = true;

    // User test data
    public static final String USER_NAME = "testuser";
    public static final String USER_EMAIL = "testuser@example.com";
    public static final String USER_PHONE = "1234567890";
    public static final String USER_WEBSITE = "https://example.com";

    // Updated user test data
    public static final String UPDATED_USER_NAME = "updateduser";
    public static final String UPDATED_USER_EMAIL = "updateduser@example.com";
    public static final String UPDATED_USER_PHONE = "0987654321";
    public static final String UPDATED_USER_WEBSITE = "https://updated.example.com";
}

