package com.automation.api.utils;

/**
 * Central place for API endpoint paths and small helpers to build path/query strings.
 * Keeps test classes free of hard-coded URL fragments.
 */
public final class Endpoints {
    private Endpoints() { }

    // Resource base paths
    public static final String POSTS = "/posts";
    public static final String COMMENTS = "/comments";
    public static final String ALBUMS = "/albums";
    public static final String PHOTOS = "/photos";
    public static final String TODOS = "/todos";
    public static final String USERS = "/users";

    // Path builders
    public static String postById(int id) {
        return POSTS + "/" + id;
    }

    public static String commentById(int id) {
        return COMMENTS + "/" + id;
    }

    public static String albumById(int id) {
        return ALBUMS + "/" + id;
    }

    public static String photoById(int id) {
        return PHOTOS + "/" + id;
    }

    public static String todoById(int id) {
        return TODOS + "/" + id;
    }

    public static String userById(int id) {
        return USERS + "/" + id;
    }

    // Query builders (simple string-based helpers)
    public static String postsByUser(int userId) {
        return POSTS + "?userId=" + userId;
    }

    public static String commentsByPost(int postId) {
        return COMMENTS + "?postId=" + postId;
    }

    public static String albumsByUser(int userId) {
        return ALBUMS + "?userId=" + userId;
    }

    public static String photosByAlbum(int albumId) {
        return PHOTOS + "?albumId=" + albumId;
    }

    public static String todosByUser(int userId) {
        return TODOS + "?userId=" + userId;
    }
}

