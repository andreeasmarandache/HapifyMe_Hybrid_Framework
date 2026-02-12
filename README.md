# HapifyMe Hybrid Automation Framework

A hybrid automation framework combining UI testing (Selenide) and API testing (Rest-Assured), designed to demonstrate a real-world enterprise automation strategy.

This project showcases how API and UI automation can work together to optimize test execution speed, reliability, and architecture scalability.

---

## Project Objective

The purpose of this framework is to demonstrate:

- Hybrid testing (UI + API)
- Cookie injection for fast authentication
- Data-driven testing with Cucumber
- Clean separation between test logic and framework logic
- Real-world login optimization strategy
- Scalable automation architecture

---

## Tech Stack

- Java
- Maven
- Selenide (UI automation)
- Rest-Assured (API automation)
- JUnit 4
- Cucumber (BDD)
- WebDriverManager
- Jackson
- Awaitility

---

## Architecture Overview

This framework follows a layered structure inside:

src/test/java/com/hapifyme/automation

### API Layer

Handles backend authentication and session management.

- AuthClient.java – performs API authentication
- LoginResponse.java – maps API response
- User.java – user model
- Cookie extraction logic for hybrid login

### UI Layer (Page Objects)

- LoginPage.java
- HomePage.java

Implements Page Object Model for maintainability and readability.

### Hybrid Logic

The hybrid login flow:

1. Open browser
2. Extract real browser User-Agent
3. Perform login via API using same User-Agent
4. Extract session cookie
5. Inject cookie into browser
6. Navigate to protected page
7. Validate successful login

This reduces UI login time and improves test stability.

---

## Project Structure

```
HapifyMe_Hybrid_Framework/
├── .github/
├── .idea/
├── src/
│   ├── test/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── hapifyme/
│   │   │           └── automation/
│   │   │               ├── api/
│   │   │               │   └── models/
│   │   │               ├── config/
│   │   │               ├── pages/
│   │   │               ├── runners/
│   │   │               ├── steps/
│   │   │               ├── tests/
│   │   │               └── utils/
│   │   └── resources/
│   │       ├── features/
│   │       └── config.properties
├── .gitignore
└── pom.xml
```

---

## Implemented Test Scenarios

### Classic UI Login (JUnit)

HybridSmokeTest.testLoginClasic()

- Opens browser
- Reads credentials from config
- Performs login via UI
- Validates successful authentication

---

### Hybrid Login (API + UI)

HybridSmokeTest.testLoginHibrid()

- Opens browser
- Extracts real User-Agent
- Authenticates via API
- Retrieves session cookie
- Injects cookie into browser
- Navigates directly to protected page
- Asserts user is logged in

Demonstrates backend-driven UI authentication.

---

### Cucumber BDD Scenarios

Located in:

src/test/resources/features/Login.feature

Includes:

- UI login scenario
- API-based authentication scenario
- Data-driven negative login testing (Scenario Outline)

Example negative scenario logic:

- Multiple invalid email/password combinations
- Error message validation using JUnit assertions

---

## Configuration Management

Configuration values are stored in:

config.properties

Managed via:

ConfigManager.java

Example properties:
- base.url.ui
- valid.email
- valid.password

---

## How to Run the Tests

Run the full test suite:

mvn clean test

---

## Key Concepts Demonstrated

- Page Object Model
- Hybrid API + UI automation
- Cookie injection strategy
- User-Agent spoofing for session consistency
- Data-driven testing with Cucumber
- JUnit assertions
- Centralized configuration handling
- Clean test structure inside single module

---

## Why Hybrid Automation?

Hybrid automation allows:

- Faster login flows
- Reduced UI flakiness
- Backend state validation
- Realistic enterprise test strategy
- Better separation of responsibilities

This approach mirrors how large-scale automation frameworks are designed in production environments.
