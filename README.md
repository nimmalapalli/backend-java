# Restaurant Platform Backend

Full shared Java Spring Boot + MongoDB backend for:

- User Kiosk Angular app — port 4200
- Admin Angular app — port 4300
- Kitchen Display Angular app — port 4400

Backend — port 8080.

## Requirements

- Java 21
- Maven 3.9+
- MongoDB 7+

## MongoDB

Default:
mongodb://localhost:27017/restaurant_platform

Environment:
MONGODB_URI=mongodb://localhost:27017/restaurant_platform

## Start

mvn spring-boot:run

or:

mvn clean package
java -jar target/restaurant-platform-backend-1.0.0.jar

## API groups

Kiosk:
GET /api/kiosk/menu
GET /api/kiosk/settings
POST /api/kiosk/customers
POST /api/kiosk/orders
GET /api/kiosk/orders/{id}/status
POST /api/kiosk/payments
POST /api/kiosk/heartbeat

Admin:
CRUD /api/admin/categories
CRUD /api/admin/products
CRUD /api/admin/modifiers
CRUD /api/admin/offers
GET /api/admin/orders
GET /api/admin/kiosks
GET /api/admin/kitchens
GET/PUT /api/admin/settings/{id}
GET /api/admin/dashboard

Kitchen:
GET /api/kitchen/orders
GET /api/kitchen/orders/{id}
PUT /api/kitchen/orders/{id}/status
GET /api/kitchen/settings
POST /api/kitchen/heartbeat

The seed data is enabled by default. Disable it with SEED_ENABLED=false after the first run.

Important:
This is a functional foundation. For production deployment, connect a real payment provider, add authentication/roles, object storage for food images, audit logging, idempotency, rate limiting, and transactional order/payment handling.

## Compilation fix

The original version relied on Lombok-generated getters/setters/builders. If annotation processing was disabled, Maven reported `cannot find symbol` for `builder()`, `get...()` and `set...()`. This corrected version removes Lombok from the domain model and provides explicit Java accessors/builders, so it does not depend on IDE annotation processing.
