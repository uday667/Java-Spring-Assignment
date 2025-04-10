# **User & Journal Services - Complete Documentation**

This guide provides **full API documentation**, **payload examples**, **setup instructions**, and **execution steps** for the **User Management** and **Journal Event Tracking** microservices.

---

## **1. System Overview**
### **Architecture**
- **User Service** (`8080`): Manages user CRUD operations, authentication, and publishes events to Kafka.
- **Journal Service** (`8081`): Consumes Kafka events and stores them in a database.
- **Kafka**: Message broker for event streaming.
- **PostgreSQL**: Database for both services.

### **Technologies**
- **Spring Boot 3** (Java)
- **Spring Security** (JWT/OAuth2 could be added)
- **Kafka** (Event streaming)
- **PostgreSQL** (Database)
- **Docker** (Containerization)

---

## **2. API Documentation**
### **User Service API (`http://localhost:8080/api/users`)**
| **Endpoint** | **Method** | **Description** | **Auth Required** |
|-------------|-----------|----------------|------------------|
| `/register` | `POST` | Register a new user | ❌ No |
| `/{id}` | `GET` | Get user by ID | ✅ Yes (Admin or Self) |
| `/` | `GET` | Get all users | ✅ Yes (Admin) |
| `/{id}` | `PUT` | Update user | ✅ Yes (Admin or Self) |
| `/{id}` | `DELETE` | Delete user | ✅ Yes (Admin or Self) |
| `/roles` | `POST` | Add role to user | ✅ Yes (Admin) |
| `/roles` | `DELETE` | Remove role from user | ✅ Yes (Admin) |

### **Journal Service API (`http://localhost:8081/api/events`)**
| **Endpoint** | **Method** | **Description** | **Auth Required** |
|-------------|-----------|----------------|------------------|
| `/` | `GET` | Get all events | ✅ Yes (Admin) |
| `/user/{userId}` | `GET` | Get events for a user | ✅ Yes (Admin or Self) |
| `/type/{eventType}` | `GET` | Get events by type | ✅ Yes (Admin) |
| `/date-range?start={start}&end={end}` | `GET` | Get events between dates | ✅ Yes (Admin) |

---

## **3. API Request & Response Payloads**
### **User Service API Payloads**
#### **1. Register User (`POST /register`)**
**Request:**
```json
{
    "username": "testuser",
    "password": "password123",
    "email": "test@example.com",
    "firstName": "John",
    "lastName": "Doe"
}
```
**Response (201 Created):**
```json
{
    "id": 1,
    "username": "testuser",
    "email": "test@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "roles": ["USER"],
    "createdAt": "2023-05-20T10:15:30",
    "updatedAt": "2023-05-20T10:15:30"
}
```

#### **2. Update User (`PUT /{id}`)**
**Request:**
```json
{
    "email": "updated@example.com",
    "firstName": "Updated"
}
```
**Response (200 OK):**
```json
{
    "id": 1,
    "username": "testuser",
    "email": "updated@example.com",
    "firstName": "Updated",
    "lastName": "Doe",
    "roles": ["USER"],
    "createdAt": "2023-05-20T10:15:30",
    "updatedAt": "2023-05-20T10:30:45"
}
```

#### **3. Add Role (`POST /roles`)**
**Request:**
```json
{
    "username": "testuser",
    "role": "ADMIN"
}
```
**Response (204 No Content)**

---

### **Journal Service API Payloads**
#### **1. Get All Events (`GET /`)**
**Response (200 OK):**
```json
[
    {
        "id": 1,
        "userId": 1,
        "username": "testuser",
        "eventType": "USER_REGISTERED",
        "timestamp": "2023-05-20T10:15:30",
        "receivedAt": "2023-05-20T10:15:31",
        "userDetails": "{\"id\":1,\"username\":\"testuser\",\"email\":\"test@example.com\"}"
    },
    {
        "id": 2,
        "userId": 1,
        "username": "testuser",
        "eventType": "USER_UPDATED",
        "timestamp": "2023-05-20T10:30:45",
        "receivedAt": "2023-05-20T10:30:46",
        "userDetails": "{\"id\":1,\"email\":\"updated@example.com\"}"
    }
]
```

#### **2. Get Events for a User (`GET /user/1`)**
**Response (200 OK):**
```json
[
    {
        "id": 1,
        "userId": 1,
        "username": "testuser",
        "eventType": "USER_REGISTERED",
        "timestamp": "2023-05-20T10:15:30",
        "receivedAt": "2023-05-20T10:15:31"
    }
]
```

---

## **4. Running the System**
### **Prerequisites**
- Docker & Docker Compose installed
- Java 17+ (for local development)

### **Steps to Run**
1. **Clone the repository** (if applicable).
2. **Run services using Docker Compose**:
   ```bash
   docker-compose up -d
   ```
   This will start:
   - PostgreSQL (for User & Journal services)
   - Kafka (for event streaming)
   - User Service (`8080`)
   - Journal Service (`8081`)

3. **Verify services are running**:
   ```bash
   docker ps
   ```
   Expected output:
   ```
   CONTAINER ID   IMAGE                PORTS                    NAMES
   ...            user-service:latest  0.0.0.0:8080->8080/tcp   user-service
   ...            journal-service      0.0.0.0:8081->8081/tcp   journal-service
   ...            postgres:13          0.0.0.0:5432->5432/tcp   postgres-user
   ...            confluentinc/cp-kafka:7.3.0 0.0.0.0:9092->9092/tcp  kafka
   ```

---

## **5. Testing the APIs**
### **1. Register a User**
```bash
curl -X POST -H "Content-Type: application/json" \
-d '{"username":"testuser","password":"password123","email":"test@example.com"}' \
http://localhost:8080/api/users/register
```

### **2. Get All Users (Admin Only)**
```bash
curl -u admin:adminpass http://localhost:8080/api/users
```

### **3. Update User (Self or Admin)**
```bash
curl -X PUT -u testuser:password123 -H "Content-Type: application/json" \
-d '{"email":"updated@example.com"}' \
http://localhost:8080/api/users/1
```

### **4. Check Journal Events**
```bash
curl -u admin:adminpass http://localhost:8081/api/events
```

---

## **6. Key Features**
✅ **User Management** (CRUD + Roles)  
✅ **Kafka Event Streaming** (User → Journal Service)  
✅ **Role-Based Access Control (RBAC)**  
✅ **Dockerized Deployment**  
✅ **Comprehensive API Documentation**  

---

## **7. Troubleshooting**
### **Common Issues**
1. **Kafka not working?**
   - Check if ZooKeeper & Kafka containers are running:
     ```bash
     docker logs kafka
     ```
   - Ensure `spring.kafka.bootstrap-servers` is correct.

2. **Database connection issues?**
   - Verify PostgreSQL is running:
     ```bash
     docker logs postgres-user
     ```
   - Check `application.yml` for correct DB credentials.

3. **Authentication failing?**
   - Ensure correct credentials:
     ```bash
     curl -u username:password http://localhost:8080/api/users/1
     ```

---

## **8. Conclusion**
This system provides:
- **User management** (registration, updates, deletion)
- **Event journaling** (Kafka + PostgreSQL)
- **Secure API access** (Spring Security)
- **Easy deployment** (Docker Compose)

**Next Steps?**
- Add **JWT Authentication** for stateless security.
- Implement **Swagger/OpenAPI** for interactive docs.
- Add **Unit/Integration Tests** (JUnit, Mockito).

🚀 **Happy Coding!** 🚀
