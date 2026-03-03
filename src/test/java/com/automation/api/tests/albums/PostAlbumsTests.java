package com.automation.api.tests.albums;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.AlbumsData;
import com.automation.api.utils.Endpoints;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("POST Albums API Tests")
public class PostAlbumsTests extends SetUp {

    @Test
    @DisplayName("Validate API creates a new album with valid data successfully")
    public void testCreateAlbum() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + AlbumsData.DEFAULT_ALBUM_ID + ",\n" +
                        "  \"title\": \"" + AlbumsData.ALBUM_TITLE + "\"\n" +
                        "}")
        .when()
                .post(Endpoints.ALBUMS)
        .then()
                .statusCode(201)
                .body("userId", equalTo(AlbumsData.DEFAULT_ALBUM_ID))
                .body("title", equalTo(AlbumsData.ALBUM_TITLE))
                .body("id", notNullValue())
                .body(matchesJsonSchemaInClasspath(AlbumsData.ALBUM_CREATE_RESPONSE_SCHEMA_PATH));
    }

    @Test
    @DisplayName("Validate API rejects album creation with empty body")
    public void testCreateAlbumWithEmptyBody() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{}")
        .when()
                .post(Endpoints.ALBUMS)
        .then()
                .extract()
                .response(); // Extract the response to perform custom assertions

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 400 || statusCode == 422,
                "Expected status 400 or 422 for album creation with empty body, got: " + statusCode);
    }
}
