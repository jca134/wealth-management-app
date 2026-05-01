# Wealth Management App

A Spring Boot web application for managing clients, advisors, accounts, assets, transactions, financial goals, and client risk profiles for a financial advisory firm.

## Requirements

- **Java 17**
- **PostgreSQL 13+** running locally on port `5432`
## Setup

### 1. Create the PostgreSQL database

Connect to your local PostgreSQL instance and create the database used by the app:
```sql
CREATE DATABASE wealth_db;
```


Make sure a PostgreSQL user exists that matches the credentials in `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/wealth_db
spring.datasource.username=jadenaguilon
spring.datasource.password=password123
```

If your local username or password is different, update those two lines before running the app.

### 2. Create the tables

The application expects the schema to already exist in `wealth_db`. Run the project's `CREATE TABLE` statements **in the order below** — each table only references tables listed above it, so creating and loading data in this order satisfies every foreign key:

1. `advisors`
2. `account_info`
3. `assets`
4. `risk_profiles`
5. `clients` — FK to `advisors`
6. `accounts` — FK to `clients`, `account_info`
7. `client_risk_assessments` — FK to `clients`, `risk_profiles`
8. `financial_goals` — FK to `clients`
9. `transactions` — FK to `accounts`, `assets`

Load data into the tables in the same order. Inserting into a child table before its parent will fail the foreign-key constraint.

## Running the app

From the project root:

```
./gradlew bootRun
```

On Windows:
```
gradlew.bat bootRun
```

The app will start on [http://localhost:8080](http://localhost:8080).

## Building

To produce an executable JAR:

```
./gradlew build
```

The output will be in `build/libs/`.

## Tech stack

- Spring Boot 3.5
- Spring MVC + Spring JDBC
- Thymeleaf (server-rendered HTML)
- PostgreSQL
- Gradle (Kotlin DSL)
