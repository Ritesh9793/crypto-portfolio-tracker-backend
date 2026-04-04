# Crypto Portfolio Tracker Backend

## 1. Introduction
This repository contains the Spring Boot backend for the Crypto Portfolio Tracker with Risk and Scam Analysis project. It provides authentication, exchange connection management, holdings and trade processing, live pricing support, risk alert generation, notifications, P&L reporting, tax hints, and AI assistant integration.

## 2. Overview
The backend is responsible for:

- user registration, login, refresh-token based session renewal, and profile management
- secure exchange account storage with encrypted API credentials
- portfolio data management for holdings, trades, prices, and snapshots
- backend-generated risk alerts and notification workflows
- P&L summaries, timelines, CSV export, and tax-ready calculations
- API endpoints consumed by the React frontend and deployed environment

## 3. Tech Stack
- Java 17+
- Spring Boot 3
- Spring Security
- Spring Data JPA
- JWT authentication with access and refresh tokens
- H2 for local/test fallback
- MySQL-compatible configuration for assignment alignment
- PostgreSQL runtime support for hosted deployment compatibility
- Maven

## 4. Architecture
The backend follows a layered architecture:

- `controller` layer exposes REST endpoints
- `service` layer handles business logic for auth, portfolio, pricing, risk, notifications, and reporting
- `repository` layer manages persistence via Spring Data JPA
- `model` layer stores entities such as `User`, `ExchangeAccount`, `Holding`, `Trade`, `PriceSnapshot`, `Notification`, `RiskAlert`, `ScamToken`, and `RefreshToken`
- `security` layer handles JWT parsing, authentication filtering, and password encoding

High-level flow:

1. The frontend authenticates through `/api/auth`.
2. Access tokens secure portfolio, pricing, risk, and reporting endpoints.
3. Exchange credentials are stored encrypted at rest and returned as masked values.
4. Holdings and trades feed pricing, P&L, tax, and dashboard summaries.
5. Risk analysis generates alerts and notifications for portfolio monitoring.

## 5. APIs
Main API groups available in the backend:

### Authentication
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/refresh`
- `POST /api/auth/forgot-password`

### Dashboard
- `GET /api/dashboard/summary`

### Exchange Connections
- `GET /api/exchange-accounts`
- `POST /api/exchange-accounts`
- `DELETE /api/exchange-accounts/{exchange}`
- `GET /api/exchange-accounts/sync/{exchange}`

### Holdings and Trades
- `GET /api/holdings`
- `POST /api/trades/add-trade`
- `GET /api/trades/get-trades`
- `PUT /api/trades/{id}`
- `DELETE /api/trades/{id}`

### Pricing and Market Data
- `GET /api/pricing`
- `GET /api/prices`
- `GET /api/prices/history/{asset}`
- `GET /api/prices/history`
- `GET /api/market/coins`

### Risk, Notifications, and AI
- `GET /api/risk-alerts`
- `GET /api/notifications`
- `GET /api/notifications/unread-count`
- `POST /api/notifications/{id}/read`
- `POST /api/notifications/read-all`
- `POST /api/ai/chat`

### Reports and Profile
- `GET /api/pnl`
- `GET /api/pnl/timeline`
- `GET /api/pnl/export`
- `GET /api/tax/hints`
- `GET /api/profile/get-profile`
- `POST /api/profile/update-profile`
- `POST /api/profile/change-password`
- `POST /api/profile/set-preferences`
- `POST /api/profile/delete-account`

## 6. Test Results
Current local verification status:

- `mvn.cmd -q -DskipTests compile` passed
- `mvn.cmd test` passed
- Spring Boot context test passed successfully on April 4, 2026

This confirms the updated backend compiles and starts successfully with the current configuration.

## 7. Documentation: Postman & Swagger
- Swagger / OpenAPI is not currently configured in this repository.
- A Postman collection is not currently committed in this repository.
- The API list above reflects the actual controller routes currently present in code.

Recommended next step:
- add Springdoc OpenAPI for live Swagger documentation
- commit a Postman collection for evaluator and deployment testing

## 8. Future Enhancements
- integrate real external scam intelligence providers such as CryptoScamDB and Etherscan
- support real exchange sync using live exchange APIs instead of tracked-holdings fallback only
- add reset-password completion endpoint and email token flow
- add role-based admin monitoring and audit logs
- add more integration and controller-level tests
- add Swagger UI and a shared Postman collection

## 9. Author - Ritesh Gupta
- Ritesh Gupta
