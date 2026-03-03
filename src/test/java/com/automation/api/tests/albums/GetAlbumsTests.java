package com.automation.api.tests.albums;

import com.automation.api.base.SetUp;
import com.automation.api.config.ApiConfig;
import com.automation.api.testdata.AlbumsData;
import com.automation.api.utils.Endpoints;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("GET Albums API Tests")
public class GetAlbumsTests extends SetUp {

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
    @DisplayName("Validate API returns a valid album with its expected fields")
    public void testGetSingleAlbum() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.albumById(AlbumsData.DEFAULT_ALBUM_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(AlbumsData.DEFAULT_ALBUM_ID))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body(matchesJsonSchemaInClasspath(AlbumsData.ALBUM_SCHEMA_PATH));
    }

    @Test
    @DisplayName("Validate API returns albums for a specific user ID")
    public void testGetAlbumsByUserId() {
        given()
                .spec(requestSpec)
                .queryParam("userId", AlbumsData.DEFAULT_ALBUM_ID)
        .when()
                .get(Endpoints.ALBUMS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(AlbumsData.DEFAULT_ALBUM_ID)));
    }

    @Test
    @DisplayName("Validate API handles non-existent album requests gracefully")
    public void testGetInvalidAlbum() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.albumById(AlbumsData.INVALID_ID))
        .then()
                .extract() 
                .response(); // Extract the response to perform custom assertions

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid album, got: " + statusCode);
    }

    @Test
    @DisplayName("Validate API returns the correct Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.albumById(AlbumsData.DEFAULT_ALBUM_ID))
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
}
