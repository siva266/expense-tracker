# Expense Tracker

Java 17 + Spring Boot 3 + Spring Data JPA + MySQL (backend) and React + Vite (frontend).

Backend (Windows): cd backend; .\mvnw.cmd spring-boot:run (set your MySQL password in application.properties)
Backend (macOS/Linux): cd backend && ./mvnw spring-boot:run (set your MySQL password in application.properties)
Frontend: cd frontend && npm install && npm run dev -> http://localhost:5173

API: GET/POST /api/expenses, GET/PUT/DELETE /api/expenses/{id}, GET /api/expenses/summary
Filters: ?category=Food&from=2026-01-01&to=2026-12-31&page=0&size=5
