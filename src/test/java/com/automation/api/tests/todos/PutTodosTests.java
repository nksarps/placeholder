package com.automation.api.tests.todos;

import com.automation.api.base.SetUp;
import com.automation.api.utils.Endpoints;
import com.automation.api.resources.TestData;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("PUT Todos API Tests")
public class PutTodosTests extends SetUp {

    @Test
    @DisplayName("Validate API updates an existing todo successfully with valid data")
    public void testUpdateTodo() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + TestData.DEFAULT_TODO_USER_ID + ",\n" +
                        "  \"id\": " + TestData.DEFAULT_TODO_ID + ",\n" +
                        "  \"title\": \"" + TestData.UPDATED_TODO_TITLE + "\",\n" +
                        "  \"completed\": " + TestData.UPDATED_TODO_COMPLETED + "\n" +
                        "}")
        .when()
                .put(Endpoints.todoById(TestData.DEFAULT_TODO_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TestData.DEFAULT_TODO_ID))
                .body("userId", equalTo(TestData.DEFAULT_TODO_USER_ID))
                .body("title", equalTo(TestData.UPDATED_TODO_TITLE))
                .body("completed", equalTo(TestData.UPDATED_TODO_COMPLETED));
    }

    @Test
    @DisplayName("Validate API handles update of invalid todo gracefully")
    public void testUpdateInvalidTodo() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + TestData.DEFAULT_TODO_USER_ID + ",\n" +
                        "  \"id\": " + TestData.INVALID_ID + ",\n" +
                        "  \"title\": \"" + TestData.UPDATED_TODO_TITLE + "\",\n" +
                        "  \"completed\": " + TestData.UPDATED_TODO_COMPLETED + "\n" +
                        "}")
        .when()
                .put(Endpoints.todoById(TestData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 404,
                "Expected status 404 for invalid todo update, got: " + statusCode);
    }
}
