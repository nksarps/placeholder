package com.automation.api.tests.todos;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.TodosData;
import com.automation.api.utils.Endpoints;

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
                        "  \"userId\": " + TodosData.DEFAULT_TODO_USER_ID + ",\n" +
                        "  \"id\": " + TodosData.DEFAULT_TODO_ID + ",\n" +
                        "  \"title\": \"" + TodosData.UPDATED_TODO_TITLE + "\",\n" +
                        "  \"completed\": " + TodosData.UPDATED_TODO_COMPLETED + "\n" +
                        "}")
        .when()
                .put(Endpoints.todoById(TodosData.DEFAULT_TODO_ID))
        .then()
                .statusCode(200)
                .body("id", equalTo(TodosData.DEFAULT_TODO_ID))
                .body("userId", equalTo(TodosData.DEFAULT_TODO_USER_ID))
                .body("title", equalTo(TodosData.UPDATED_TODO_TITLE))
                .body("completed", equalTo(TodosData.UPDATED_TODO_COMPLETED));
    }

    @Test
    @DisplayName("Validate API handles update of invalid todo gracefully")
    public void testUpdateInvalidTodo() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + TodosData.DEFAULT_TODO_USER_ID + ",\n" +
                        "  \"id\": " + TodosData.INVALID_ID + ",\n" +
                        "  \"title\": \"" + TodosData.UPDATED_TODO_TITLE + "\",\n" +
                        "  \"completed\": " + TodosData.UPDATED_TODO_COMPLETED + "\n" +
                        "}")
        .when()
                .put(Endpoints.todoById(TodosData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertEquals(404, statusCode, "Expected status 404 for invalid todo update, got: " + statusCode);
    }
}
