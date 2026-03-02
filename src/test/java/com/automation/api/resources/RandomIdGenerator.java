package com.automation.api.resources;

/**
 * Utility class to generate random IDs for testing purposes.
 */
public final class RandomIdGenerator {
    /**
     * Generates a random user ID between 1 and 10.
     *
     * @return a random user ID
     */
    public static int getRandomUserId() {
        return (int) (Math.random() * 10) + 1; // Generates a number between 1 and 10
    }

    /**
     * Generates a random post ID between 1 and 100.
     *
     * @return a random post ID
     */
    public static int getRandomPostId() {
        return (int) (Math.random() * 100) + 1; // Generates a number between 1 and 100
    }

    /**
     * Generates a random comment ID between 1 and 500.
     *
     * @return a random comment ID
     */
    public static int getRandomCommentId() {
        return (int) (Math.random() * 500) + 1; // Generates a number between 1 and 500
    }

    /**
     * Generates a random album ID between 1 and 100.
     *
     * @return a random album ID
     */
    public static int getRandomAlbumId() {
        return (int) (Math.random() * 100) + 1; // Generates a number between 1 and 100
    }

    /**
     * Generates a random photo ID between 1 and 5000.
     *
     * @return a random photo ID
     */
    public static int getRandomPhotoId() {
        return (int) (Math.random() * 5000) + 1; // Generates a number between 1 and 5000
    }

    /**
     * Generates a random todo ID between 1 and 200.
     *
     * @return a random todo ID
     */
    public static int getRandomTodoId() {
        return (int) (Math.random() * 200) + 1; // Generates a number between 1 and 200
    }
}