package com.automation.api.config;

import io.github.cdimascio.dotenv.Dotenv;
/**
 * Configuration class for API testing
 * Contains all configuration constants and methods for REST Assured tests
 */
public class ApiConfig {
    // Getting base URL from the environment variable
    private static final Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
    private static final String BASE_URL = dotenv.get("BASE_URL");

    // Base URI constant for JSONPlaceholder API
    private static final String BASE_URI = BASE_URL;

    // Common headers
    private static final String CONTENT_TYPE = "application/json";
    private static final String ACCEPT = "application/json";
    private static final String CHARSET = "UTF-8";

    /**
     * Get the base URI for the API
     *
     * @return the base URI string
     */
    public static String getBaseUri() {
        return BASE_URI;
    }

    /**
     * Get the Content-Type header value
     *
     * @return the Content-Type header value
     */
    public static String getContentType() {
        return CONTENT_TYPE;
    }

    /**
     * Get the Accept header value
     *
     * @return the Accept header value
     */
    public static String getAcceptHeader() {
        return ACCEPT;
    }

    /**
     * Get the charset for requests
     *
     * @return the charset value
     */
    public static String getCharset() {
        return CHARSET;
    }

    /**
     * Get the full Content-Type header with charset
     *
     * @return the full Content-Type header value
     */
    public static String getContentTypeWithCharset() {
        return CONTENT_TYPE + "; charset=" + CHARSET;
    }
}

