# Project Title

Placeholder

# Project Overview

This project is a Maven-based Java testing framework designed to validate RESTful APIs using Rest Assured and JUnit 5. It includes a CI/CD pipeline for automated testing and reporting.

## What the Test Suite Does

- Validates API endpoints for JSONPlaceholder.
- Covers positive and negative test scenarios.
- Generates detailed Allure reports for test results.

## Technologies Used

- Java 21
- Maven
- JUnit 5
- Rest Assured
- Allure Reporting

## High-Level Goals

- Ensure API reliability and correctness.
- Automate testing workflows.
- Provide detailed reporting for debugging and analysis.

# Purpose and Scope

## What the Project Validates

- API endpoint functionality.
- JSON schema validation.
- Response time and status codes.

## Types of Workflows Covered

- Positive and negative test cases.
- Data-driven testing.
- CI/CD reproducibility.

# Prerequisites

## Required Software

- Java Development Kit (JDK)
- Apache Maven
- Git

## Required Versions

- Java: 21
- Maven: 3.9
- Browser: Latest Chrome

## Dependencies/Tools Before Setup

- Internet access
- Optional: Allure CLI for local report viewing

# Quick Start

## Clone the Repository

```bash
git clone https://github.com/nksarps/placeholder
cd placeholder
```

## Run Locally

### Environment Setup

1. Create a `.env` file with the following variables:
   ```
   BASE_URL=https://jsonplaceholder.typicode.com
   ```

### Execute Tests

```bash
mvn clean test
```

### Generate and View Allure Report

```bash
mvn allure:report
mvn allure:serve
```

# Project Structure

## Directory Tree

```
placeholder
├── src
│   ├── main
│   │   └── java
│   ├── test
│   │   ├── java
│   │   └── resources
├── .github
│   └── workflows
├── allure-results
├── target
├── pom.xml
├── Dockerfile
└── .env
```

## Major Directories and Files Explanation

- `src/main/java/...`: Application source code.
- `src/test/java/...`: Test cases.
- `src/test/resources/...`: Test resources (e.g., data files).
- `.github/workflows`: CI/CD pipeline configuration.
- `pom.xml`: Maven project configuration.
- `allure-results`: Raw test results for Allure.
- `target`: Compiled code and test artifacts.
- `.env`: Environment variables.

# Features

- Functional flows covered.
- Positive and negative test cases.
- Data-driven testing.
- Allure reporting.
- CI integration.

# CI/CD Pipeline

## Workflow File

- Location: `.github/workflows/ci.yml`

## When It Runs

- Push to `main`, `develop`, or `feature/*` branches.
- Pull requests to `main` or `develop`.
- Manual trigger.

# Build Status Badge

![Build Status](https://github.com/<user>/<repo>/actions/workflows/ci.yml/badge.svg)

# Configuration

## Local Setup

- Use `.env` for environment variables.
- Required keys:
  - `BASE_URL`

## Environment Variables

### Local/Runtime

- `BASE_URL`

### CI Environment

- Secrets used:
  - `BASE_URL`
  - `MAVEN_OPTS`
  

# Secrets

- `BASE_URL`: API base URL.
- `NOTIFY_EMAIL_FROM`: Email address used as the sender for notifications.
- `NOTIFY_EMAIL_TO`: Email address used as the recipient for notifications.
- `SLACK_WEBHOOK_URL`: Webhook URL for sending Slack notifications.
- `SMTP_PASSWORD`: Password for the SMTP server.
- `SMTP_PORT`: Port number for the SMTP server.
- `SMTP_SERVER`: Address of the SMTP server.
- `SMTP_USERNAME`: Username for the SMTP server.

- **Security warning**: Do not commit secrets.


# Contributing

## Branch Strategy

- `main`
- `develop`
- `feature/*`

## Commit Guidelines

- Follow conventional commits.
- Write clear messages.

## Pull Request Process

1. Run local checks.
2. Open PR.
3. Ensure CI passes.

# Support

## Where to Get Help

- Open an issue.
- Contact maintainers.
