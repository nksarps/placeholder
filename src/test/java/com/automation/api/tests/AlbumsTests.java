package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.ApiConfig;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Albums API Tests")
public class AlbumsTests extends SetUp {

    @Test
    @DisplayName("Validate API returns all albums")
    public void testGetAllAlbums() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.ALBUMS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("Validate API returns single album with expected fields")
    public void testGetSingleAlbum() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.albumById(TestData.DEFAULT_ALBUM_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_ALBUM_ID))
                .body("userId", notNullValue())
                .body("title", notNullValue());
    }

    @Test
    @DisplayName("Validate API returns albums for specific userId")
    public void testGetAlbumsByUserId() {
        given()
                .spec(requestSpec)
                .queryParam("userId", TestData.DEFAULT_USER_ID)
        .when()
                .get(Endpoints.ALBUMS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(TestData.DEFAULT_USER_ID)));
    }

    @Test
    @DisplayName("Validate API handles invalid album request gracefully")
    public void testGetInvalidAlbum() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.albumById(TestData.INVALID_ID))
        .then()
                .extract() 
                .response(); // Extract the response to perform custom assertions

        int statusCode = resp.statusCode();
        String body = resp.asString().trim();

        assertTrue(statusCode == 200 || statusCode == 404,
                "Expected status 200 or 404 for invalid album, got: " + statusCode);

        // If status is 200, we expect an empty object or empty body for non-existent album
        if (statusCode == 200) {
            boolean isEmptyObject = "{}".equals(body) || body.isEmpty();
            assertTrue(isEmptyObject, "Expected empty object or empty body for non-existent album, got: " + body);
        }
    }

    @Test
    @DisplayName("Validate API Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.albumById(TestData.DEFAULT_ALBUM_ID))
        .then()
                .statusCode(200)
                .extract()
                .response(); // Extract the response to perform custom assertions

        // Check that Content-Type header is present and contains application/json
        String contentType = resp.getHeader("Content-Type");
        assertNotNull(contentType, "Content-Type header should be present");
        assertTrue(contentType.toLowerCase().contains(ApiConfig.getContentType()), "Expected Content-Type to contain 'application/json' but was: " + contentType);
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains(ApiConfig.getCharset()), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }

    @Test
    @DisplayName("Validate API creates a new album and returns status 201")
    public void testCreateAlbum() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + TestData.DEFAULT_USER_ID + ",\n" +
                        "  \"title\": \"" + TestData.ALBUM_TITLE + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.ALBUMS)
        .then()
                .statusCode(201)
                .body("userId", equalTo(TestData.DEFAULT_USER_ID))
                .body("title", equalTo(TestData.ALBUM_TITLE))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Validate API updates an existing album")
    public void testUpdateAlbum() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + TestData.DEFAULT_USER_ID + ",\n" +
                        "  \"id\": " + TestData.DEFAULT_ALBUM_ID + ",\n" +
                        "  \"title\": \"" + TestData.UPDATED_ALBUM_TITLE + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.albumById(TestData.DEFAULT_ALBUM_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_ALBUM_ID))
                .body("userId", equalTo(TestData.DEFAULT_USER_ID))
                .body("title", equalTo(TestData.UPDATED_ALBUM_TITLE));
    }

    @Test
    @DisplayName("Validate API deletes an album")
    public void testDeleteAlbum() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.albumById(TestData.DEFAULT_ALBUM_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }
}
