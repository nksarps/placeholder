package com.automation.api.tests.albums;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.AlbumsData;
import com.automation.api.utils.Endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PUT Albums API Tests")
public class PutAlbumsTests extends SetUp {

    @Test
    @DisplayName("Validate API updates an existing album with valid data successfully")
    public void testUpdateAlbum() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + AlbumsData.DEFAULT_ALBUM_ID + ",\n" +
                        "  \"id\": " + AlbumsData.DEFAULT_ALBUM_ID + ",\n" +
                        "  \"title\": \"" + AlbumsData.UPDATED_ALBUM_TITLE + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.albumById(AlbumsData.DEFAULT_ALBUM_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(AlbumsData.DEFAULT_ALBUM_ID))
                .body("userId", equalTo(AlbumsData.DEFAULT_ALBUM_ID))
                .body("title", equalTo(AlbumsData.UPDATED_ALBUM_TITLE));
    }

    @Test
    @DisplayName("Validate API handles updates of non-existent albums gracefully")
    public void testUpdateInvalidAlbum() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + AlbumsData.DEFAULT_ALBUM_ID + ",\n" +
                        "  \"id\": " + AlbumsData.INVALID_ID + ",\n" +
                        "  \"title\": \"" + AlbumsData.UPDATED_ALBUM_TITLE + "\"\n" +
                        "}")
        .when()
                .put(Endpoints.albumById(AlbumsData.INVALID_ID))
        .then()
                .extract()
                .response(); // Extract the response to perform custom assertions

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid album update, got: " + statusCode);
    }
}
