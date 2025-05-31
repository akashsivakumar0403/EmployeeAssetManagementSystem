# 🏭 Enterprise Asset Management System (EAMS)

A Spring Boot-based simulation project that monitors and manages industrial assets in real-time using simulated IoT sensor data. Developed under the domain of **Information Technology and Operations Management**.

---

## 📌 Domain

Manufacturing / Energy

---

## 🎯 Objectives

- Digitally manage and track industrial assets.
- Register and authenticate users with roles (Manager/Operator).
- Simulate and monitor sensor readings (temperature, pressure).
- Trigger alerts on anomalies and send according emails.
- Maintain logs for up-time and maintenance.

---

## 🧱 Tech Stack

| Layer        | Technology                    |
|--------------|-------------------------------|
| Framework    | Spring Boot                   |
| Security     | Spring Security + JWT (optional) |
| Persistence  | Spring Data JPA (Hibernate)   |
| Database     | MySQL Workbench               |
| Build Tool   | Maven                         |
| Utilities    | Swagger, Lombok (optional)    |
| Testing      | JUnit                         |
| Documentation| Swagger (springdoc-openapi)   |

---

## 🧩 Key Modules

1. **User & Role Management**
   - User registration/login
   - Role assignment (Operator, Manager)
   - JWT token generation & validation (optional)

2. **Asset Management**
   - CRUD on assets
   - Assign assets to users
   - Track asset location, type, and threshold limits

3. **Sensor Data Ingestion (Simulation)**
   - Simulated API to push sensor data
   - Store readings with timestamp
   - Trigger alerts if data exceeds threshold

4. **Alert Management**
   - Create alerts based on rules
   - Store active/resolved alerts
   - Notification simulation (email/log console)

5. **Maintenance & Uptime Logs**
   - Record scheduled maintenance
   - Auto-calculate uptime/downtime
   - Generate daily/weekly reports

---

## 🔐 Roles & Access

| Role     | Access Description                            |
|----------|-----------------------------------------------|
| Manager  | Full access, manage assets and users          |
| Operator | Manage only assigned assets, log maintenance  |

---

## 🗃 Sample Configuration (`application.properties`)

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/eams_db
spring.datasource.username=team_user
spring.datasource.password=localpass123

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

springdoc.api-docs.enabled=true
```

---

## 📁 Project Structure

```
com.bridgelabz.eams
├── controller
├── dto
├── entity
├── repository
├── service
├── config
├── security
├── exception
└── EamsApplication.java
```

---

## ▶️ How to Run the Project

### 🛠 Prerequisites

- Java 21+
- Maven
- MySQL Workbench
- (Optional) Postman or Swagger UI for API testing

### 🚀 Steps

1. **Clone the Repository**
```bash
git clone https://github.com/akashsivakumar0403/EnterpriseAssetMonitoringSystem.git
cd EnterpriseAssetMonitoringSystem
```

2. **Set Up the Database**
- Create a MySQL database named `eams_db` in MySQL Workbench.

3. **Update `application.properties`** (already preconfigured)

4. **Build & Run the Application**
```bash
mvn clean install
mvn spring-boot:run
```

5. **Access Swagger UI**
```
http://localhost:8080/swagger-ui.html
```

---

## 🧪 API Endpoints (Examples)

- `POST /api/auth/register` – Register a new user
- `POST /api/auth/login` – Authenticate & get JWT token
- `POST /api/assets` – Create a new asset
- `GET /api/assets` – List all assets
- `POST /api/sensors/send-data` – Push sensor data
- `GET /api/alerts` – View active/resolved alerts
- `POST /api/maintenance` – Record maintenance

---

## 👥 Authors


-----------------------------------------------------------------------------------------------------------------
 [Akash Sivakumar](https://github.com/akashsivakumar0403) - Project Lead & Backend Developer  
 [Alapan Konar](https://github.com/Alapan-100)- Sensor Data Implementation and testing    
 [Madhumitha A](https://github.com/madhu1703)- Maintence and uptime logs & UI testing  
 [Inuganti Sai Sri Teja](https://github.com/SAISRITEJAINUGANTI) - User module & testing                 
 [Relli Sai Rupesh Naidu](https://github.com/rupesh9632) - Asset module & testing                                                                                         
 [Akash Kumar Singh](https://github.com/Akashsingh3009) - Alerts & Notification  & Testing                                 

-----------------------------------------------------------------------------------------------------------------

---

## 🙏 Acknowledgments

- Capgemini CG Simulation Guidelines  
- Spring Boot & Spring Security Teams  
- MySQL Community  
- Swagger / SpringDoc

---

## 📜 GitHub Repository

[EnterpriseAssetMonitoringSystem](https://github.com/akashsivakumar0403/EnterpriseAssetMonitoringSystem)

## 🤝 Contributors
Thanks to everyone who contributed through feedback, testing, or documentation.
