<div align="center">

# 📧 Simple Invoicing

A Spring Boot invoicing application with role-based access control.

[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=Simple-Invoicing&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=Simple-Invoicing)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Simple-Invoicing&metric=bugs)](https://sonarcloud.io/summary/new_code?id=Simple-Invoicing)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=Simple-Invoicing&metric=coverage)](https://sonarcloud.io/summary/new_code?id=Simple-Invoicing)
[![Deployment Status](https://img.shields.io/badge/Deployment-Live-brightgreen?style=flat&logo=northflank&logoColor=white)](https://web--simple-invoicing--sb2tbz4x9kl8.code.run)
[![MIT License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

---

### 🎯 Final project for the **[Spring Advanced - June 2020](https://softuni.bg/trainings/3026/spring-advanced-june-2020/internal)** course. 
This educational project demonstrates user management, company handling, and automated scheduling features.

</div>

---

## 🏗️ Technology Stack

### ⚙️ Backend

- ![Java 17](https://img.shields.io/badge/Java-17-007396?style=flat&logo=openjdk&logoColor=white)
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.5-6DB33F?style=flat&logo=spring-boot&logoColor=white)
- ![Spring MVC](https://img.shields.io/badge/Spring%20MVC-Web-6DB33F?style=flat&logo=spring&logoColor=white)
- ![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-Persistence-6DB33F?style=flat&logo=spring&logoColor=white)
- ![Spring Security](https://img.shields.io/badge/Spring%20Security-Auth-6DB33F?style=flat&logo=spring&logoColor=white)
- ![Validation](https://img.shields.io/badge/Spring%20Validation-Bean%20Validation-6DB33F?style=flat&logo=spring&logoColor=white)

### 🎨 Frontend

- ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-Template%20Engine-005F0F?style=flat&logo=thymeleaf&logoColor=white)
- ![Thymeleaf Security](https://img.shields.io/badge/Thymeleaf-Spring%20Security%20Extras-005F0F?style=flat&logo=thymeleaf&logoColor=white)

### 🗄️ Database

- ![MySQL](https://img.shields.io/badge/MySQL-Development-4479A1?style=flat&logo=mysql&logoColor=white) **Primary
  database (development)**
- ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Production-4169E1?style=flat&logo=postgresql&logoColor=white) *
  *Production database support**
- ![H2](https://img.shields.io/badge/H2-In--Memory-5D5D5D?style=flat) **In-memory database (testing)**

### 📚 Additional Libraries

- ![ModelMapper](https://img.shields.io/badge/ModelMapper-Object%20Mapping-5D5D5D?style=flat)
- ![Cloudinary](https://img.shields.io/badge/Cloudinary-Media%20Management-3448C5?style=flat&logo=cloudinary&logoColor=white)
- ![Apache Commons](https://img.shields.io/badge/Apache%20Commons%20Lang-Utilities-D22128?style=flat&logo=apache&logoColor=white)

### 🛠️ Development & Testing

- ![Spring Boot DevTools](https://img.shields.io/badge/Spring%20Boot-DevTools-6DB33F?style=flat&logo=spring-boot&logoColor=white)
- ![JaCoCo](https://img.shields.io/badge/JaCoCo-Code%20Coverage-5D5D5D?style=flat)
- ![Apache Tomcat](https://img.shields.io/badge/Apache%20Tomcat-Embedded-F8DC75?style=flat&logo=apachetomcat&logoColor=black)
- ![Maven](https://img.shields.io/badge/Apache%20Maven-Build%20Tool-C71A36?style=flat&logo=apachemaven&logoColor=white)

---

## ✨ Features

### 🔐 **Roles & Users**

- 3 role types: `ADMIN`, `ROUTE`, `USER`
- The first user gets full admin rights
- New users need admin approval
- Role-based access control

### 🏢 **Companies**

- Name, address, and unique identifier
- The first company becomes an invoice issuer
- All others are contractors

### 📦 **Items**

- Name, price, image, and VAT value
- Cloudinary image upload with fallback
- Session-based storage system

### 🧾 **Invoices**

- Auto-generated numbers and tracking
- Payment types: cash or bank transfer
- Status workflow: `AWAIT` → `COMPLETE`
- Users see only their own invoices (non-admin)

### 📊 **Automation**

- Request logging with interceptors
- Scheduled log cleanup (every 5 minutes)
- Automatic invoice status updates

---

## 🚀 Quick Start

1. **Clone the repository**
   ```bash
   git clone https://github.com/mark79-github/Simple-Invoicing.git
   ```

2. **Configure either MySQL or PostgreSQL database connection**

3. **Configure Cloudinary credentials**

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access at** `http://localhost:8080`

---

<div align="center">

### 🌟 **Star this repository if you find it helpful!**

_Made with ❤️ and lots of ☕_

</div>