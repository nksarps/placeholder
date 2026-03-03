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

@DisplayName("POST Todos API Tests")
public class PostTodosTests extends SetUp {

    @Test
    @DisplayName("Validate API creates a new todo with valid data successfully")
    public void testCreateTodo() {
        given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"userId\": " + TodosData.DEFAULT_TODO_USER_ID + ",\n" +
                        "  \"title\": \"" + TodosData.TODO_TITLE + "\",\n" +
                        "  \"completed\": " + TodosData.TODO_COMPLETED + "\n" +
                        "}")
        .when()
                .post(Endpoints.TODOS)
        .then()
                .statusCode(201)
                .body("userId", equalTo(TodosData.DEFAULT_TODO_USER_ID))
                .body("title", equalTo(TodosData.TODO_TITLE))
                .body("completed", equalTo(TodosData.TODO_COMPLETED))
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Validate API rejects todo creation with missing userId field")
    public void testCreateTodoWithMissingUserId() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "  \"title\": \"" + TodosData.TODO_TITLE + "\",\n" +
                        "  \"completed\": " + TodosData.TODO_COMPLETED + "\n" +
                        "}")
        .when()
                .post(Endpoints.TODOS)
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 400 || statusCode == 422,
                "Expected status 400 or 422 for todo creation with missing userId, got: " + statusCode);
    }

    @Test
    @DisplayName("Validate API rejects todo creation with empty body")
    public void testCreateTodoWithEmptyBody() {
        Response resp = given()
                .spec(requestSpec)
                .contentType(ContentType.JSON)
                .body("{}")
        .when()
                .post(Endpoints.TODOS)
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertTrue(statusCode == 400 || statusCode == 422,
                "Expected status 400 or 422 for todo creation with empty body, got: " + statusCode);
    }
}
