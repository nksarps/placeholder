package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.Endpoints;
import com.automation.api.config.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Albums API GET Tests")
public class AlbumsTests extends SetUp {

    @Test
    @DisplayName("GET /albums - should return all albums")
    public void testGetAllAlbums() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.ALBUMS)
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0));
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
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("userId", everyItem(equalTo(TestData.DEFAULT_USER_ID)));
    }
}
