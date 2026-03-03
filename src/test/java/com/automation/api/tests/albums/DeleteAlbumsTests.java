package com.automation.api.tests.albums;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.AlbumsData;
import com.automation.api.utils.Endpoints;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DELETE Albums API Tests")
public class DeleteAlbumsTests extends SetUp {

    @Test
    @DisplayName("Validate API deletes an album that exists successfully")
    public void testDeleteAlbum() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.albumById(AlbumsData.DEFAULT_ALBUM_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    @DisplayName("Validate API handles deletion of albums that do not exist gracefully")
    public void testDeleteInvalidAlbum() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.albumById(AlbumsData.INVALID_ID))
        .then()
                .extract()
                .response(); // Extract the response to perform custom assertions

        int statusCode = resp.statusCode();

        assertEquals(404, statusCode, "Expected status 404 for invalid album deletion, got: " + statusCode);
    }
}
