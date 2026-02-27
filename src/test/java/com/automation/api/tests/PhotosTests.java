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

@DisplayName("Photos API Tests")
public class PhotosTests extends SetUp {

    @Test
    @DisplayName("GET /photos - should return all photos")
    public void testGetAllPhotos() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.PHOTOS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @DisplayName("GET /photos/1 - should return single photo with expected fields")
    public void testGetSinglePhoto() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.photoById(TestData.DEFAULT_PHOTO_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_PHOTO_ID))
                .body("albumId", notNullValue())
                .body("title", notNullValue())
                .body("url", notNullValue())
                .body("thumbnailUrl", notNullValue());
    }

    @Test
    @DisplayName("GET /photos?albumId=1 - should return photos for albumId=1")
    public void testGetPhotosByAlbumId() {
        given()
                .spec(requestSpec)
                .queryParam("albumId", TestData.DEFAULT_PHOTO_ALBUM_ID)
        .when()
                .get(Endpoints.PHOTOS)
        .then()
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("albumId", everyItem(equalTo(TestData.DEFAULT_PHOTO_ALBUM_ID)));
    }

    @Test
    @DisplayName("GET /photos/99999 - invalid photo should return empty object or 404")
    public void testGetInvalidPhoto() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.photoById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();
        String body = resp.asString().trim();

        assertTrue(statusCode == 200 || statusCode == 404,
                "Expected status 200 or 404 for invalid photo, got: " + statusCode);

        if (statusCode == 200) {
            boolean isEmptyObject = "{}".equals(body) || body.isEmpty();
            assertTrue(isEmptyObject, "Expected empty object or empty body for non-existent photo, got: " + body);
        }
    }

    @Test
    @DisplayName("GET /photos/1 - validate Content-Type header and charset")
    public void testValidateContentType() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.photoById(TestData.DEFAULT_PHOTO_ID))
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
    @DisplayName("POST /photos - should create a new photo with status 201")
    public void testCreatePhoto() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"albumId\": " + TestData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"title\": \"" + TestData.PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + TestData.PHOTO_URL + "\",\n" +
                        "  \"thumbnailUrl\": \"" + TestData.PHOTO_THUMBNAIL_URL + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.PHOTOS)
        .then()
                .statusCode(201)
                .body("albumId", equalTo(TestData.DEFAULT_PHOTO_ALBUM_ID))
                .body("title", equalTo(TestData.PHOTO_TITLE))
                .body("url", equalTo(TestData.PHOTO_URL))
                .body("thumbnailUrl", equalTo(TestData.PHOTO_THUMBNAIL_URL))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("PUT /photos/1 - should update an existing photo with status 200")
    public void testUpdatePhoto() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"albumId\": " + TestData.DEFAULT_PHOTO_ALBUM_ID + ",\n" +
                        "  \"id\": " + TestData.DEFAULT_PHOTO_ID + ",\n" +
                        "  \"title\": \"" + TestData.UPDATED_PHOTO_TITLE + "\",\n" +
                        "  \"url\": \"" + TestData.UPDATED_PHOTO_URL + "\",\n" +
                        "  \"thumbnailUrl\": \"" + TestData.UPDATED_PHOTO_THUMBNAIL_URL + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.photoById(TestData.DEFAULT_PHOTO_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_PHOTO_ID))
                .body("albumId", equalTo(TestData.DEFAULT_PHOTO_ALBUM_ID))
                .body("title", equalTo(TestData.UPDATED_PHOTO_TITLE))
                .body("url", equalTo(TestData.UPDATED_PHOTO_URL))
                .body("thumbnailUrl", equalTo(TestData.UPDATED_PHOTO_THUMBNAIL_URL));
    }

    @Test
    @DisplayName("DELETE /photos/1 - should delete photo and return status 200 or 204")
    public void testDeletePhoto() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.photoById(TestData.DEFAULT_PHOTO_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }
}
