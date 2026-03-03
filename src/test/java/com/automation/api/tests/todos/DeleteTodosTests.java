package com.automation.api.tests.todos;

import com.automation.api.base.SetUp;
import com.automation.api.testdata.TodosData;
import com.automation.api.utils.Endpoints;

import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DELETE Todos API Tests")
public class DeleteTodosTests extends SetUp {

    @Test
    @DisplayName("Validate API deletes a todo that exists successfully")
    public void testDeleteTodo() {
        given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.todoById(TodosData.DEFAULT_TODO_ID))
        .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)));
    }

    @Test
    @DisplayName("Validate API handles deletion of invalid todo gracefully")
    public void testDeleteInvalidTodo() {
        Response resp = given()
                .spec(requestSpec)
        .when()
                .delete(Endpoints.todoById(TodosData.INVALID_ID))
        .then()
                .extract()
                .response();

        int statusCode = resp.statusCode();

        assertEquals(404, statusCode, "Expected status 404 for invalid todo deletion, got: " + statusCode);
    }
}
