package com.automation.api.tests;

import com.automation.api.base.SetUp;
import com.automation.api.config.Endpoints;
import com.automation.api.config.TestData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@DisplayName("Photos API GET Tests")
public class PhotosTests extends SetUp {

    @Test
    @DisplayName("GET /photos - should return all photos")
    public void testGetAllPhotos() {
        given()
                .spec(requestSpec)
        .when()
                .get(Endpoints.PHOTOS)
        .then()
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0));
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
                .spec(responseSpec)
                .statusCode(200)
                .body("size()", greaterThan(0))
                .body("albumId", everyItem(equalTo(TestData.DEFAULT_PHOTO_ALBUM_ID)));
    }
}
