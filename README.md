# InterviewQ – QA Automation Exercise

A ready-to-run Cucumber + REST Assured starter project used during QA Automation interviews.



## 📦 What Your Project Includes

### ✅ Tech stack

| Tool | Purpose |
|---|---|
| **Java 17** | Language |
| **Maven** | Build & dependency management |
| **REST Assured 5** | HTTP client / API assertions |
| **Cucumber 7** | BDD framework (Gherkin feature files) |
| **JUnit 5 Platform** | Test runner |
| **PicoContainer** | Dependency injection between step classes |
| **Jackson** | JSON ↔ Java model mapping |
| **AssertJ** | Fluent assertion library |
| **Lombok** | Boilerplate reduction for model classes |


### ✅ All Dependencies Installed (in pom.xml)
```
✅ REST Assured 5.4.0          → API testing
✅ Cucumber 7.15.0              → BDD framework
✅ JUnit 5 + Platform Suite    → Test execution
✅ Jackson 2.17.0               → JSON serialization
✅ AssertJ 3.25.3               → Fluent assertions
✅ Lombok 1.18.32               → Boilerplate reduction
```

### ✅ Complete Project Structure
```
src/main/java/com/interviewq/
├── config/ApiConfig.java          ← ALL STAPI endpoints configured
└── models/                         ← JSON response models ready
    ├── Character.java
    ├── CharacterSearchResponse.java
    └── Page.java

src/test/java/com/interviewq/
├── runner/RunCucumberTest.java     ← Works in IDE + Maven
├── stepdefs/CharacterSteps.java    ← 11 step definitions implemented
├── hooks/Hooks.java                ← REST Assured setup/teardown
└── context/ScenarioContext.java    ← State sharing between steps

src/test/resources/
├── features/character.feature      ← 7 BDD test scenarios
└── junit-platform.properties       ← Test configuration
```

## Running the tests

### All non-WIP tests (default)
```bash
mvn test
```

### Only smoke tests
```bash
mvn test -Dcucumber.filter.tags="@smoke"
```

### Include WIP scenarios (exercises for the candidate)
```bash
mvn test -Dcucumber.filter.tags="@wip"
```

### HTML report
After a run, open `target/cucumber-reports/report.html` in a browser.

---

## API under test

**STAPI** – a public Star Trek API  
Base URL: `https://stapi.co/api/v2/rest`

Useful endpoints to explore:

| Method | Path | Description |
|---|---|---|
| `GET` | `/character/search` | Paginated character search |
| `GET` | `/character/{uid}` | Single character by UID |
| `GET` | `/animal/search` | Paginated animal search |
| `GET` | `/species/search` | Paginated species search |

Full Swagger UI: https://stapi.co/api/v2/rest/swagger-ui.html

---

## Changing the API

All endpoint constants live in `ApiConfig.java`.  
To switch to a completely different API, update `BASE_URL` and the endpoint constants there – no other file needs to change.

---

## 🚀 Quick Start

### Run Tests from Terminal
```bash
# All tests (excluding @wip)
mvn test

# Only @smoke tests
mvn test -D"cucumber.filter.tags=@smoke"

# Specific feature
mvn test -D"cucumber.filter.tags=@starapi"
```

### Run Tests from IDE
1. Open `src/test/resources/features/character.feature`
2. Click green ▶ arrow next to any scenario
3. Watch it run in the bottom panel ✅

---

