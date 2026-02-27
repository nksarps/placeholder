package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.Endpoints;
import com.automation.api.config.TestData;
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
    @DisplayName("GET /albums - should return all albums")
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
    @DisplayName("GET /albums/1 - should return single album with expected fields")
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
    @DisplayName("GET /albums?userId=1 - should return albums for userId=1")
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
    @DisplayName("GET /albums/99999 - invalid album should return empty object or 404")
    public void testGetInvalidAlbum() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.albumById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();
        String body = resp.asString().trim();

        assertTrue(statusCode == 200 || statusCode == 404,
                "Expected status 200 or 404 for invalid album, got: " + statusCode);

        if (statusCode == 200) {
            boolean isEmptyObject = "{}".equals(body) || body.isEmpty();
            assertTrue(isEmptyObject, "Expected empty object or empty body for non-existent album, got: " + body);
        }
    }

    @Test
    @DisplayName("GET /albums/1 - validate Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.albumById(TestData.DEFAULT_ALBUM_ID))
        .then()
                .statusCode(200)
                .extract()
                .response();

        String contentType = resp.getHeader("Content-Type");
        assertNotNull(contentType, "Content-Type header should be present");
        assertTrue(contentType.toLowerCase().contains("application/json"), "Expected Content-Type to contain 'application/json' but was: " + contentType);
        if (contentType.toLowerCase().contains("charset")) {
            assertTrue(contentType.toLowerCase().contains("utf-8"), "Expected charset to be UTF-8 when present, but was: " + contentType);
        }
    }

    @Test
    @DisplayName("POST /albums - should create a new album with status 201")
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
    @DisplayName("PUT /albums/1 - should update an existing album with status 200")
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
    @DisplayName("DELETE /albums/1 - should delete album and return status 200 or 204")
    public void testDeleteAlbum() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.albumById(TestData.DEFAULT_ALBUM_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }
}
