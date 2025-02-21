# Employee Management System (EMS) 

A full-stack web application for managing employees with **authentication, role-based access control, and secure API interactions** using **Spring Boot, React.js, and MySQL**.

## Features

✅ **User Authentication & JWT Authorization**  
✅ **Employee Management (CRUD Operations)**  
✅ **Role-Based Access Control (Admin/User Permissions)**  
✅ **Secure REST APIs with Spring Security**  
✅ **React-based UI with Protected Routes**  
✅ **Error Handling & Form Validations**  
✅ **Pagination & Sorting for Large Data Handling**  
✅ **CORS Configuration for Cross-Origin Requests**  

---

## 🛠️ **Tech Stack**

**Frontend:**  
- React.js (Vite, Axios, React Router)  
- Boostrap 5 (or your styling approach)  

**Backend:**  
- Java (Spring Boot, Spring Security, Spring Data JPA)  
- MySQL (Database)  
- JWT for Authentication  

---

## 🏗️ **Project Setup**  

### ⚡ **1. Clone the Repository**
```bash
git clone https://github.com/Vetri75/employee-management-system.git
cd employee-management-system
```

---

### ⚡ **2. Backend Setup (Spring Boot)**
#### ➤ **Pre-requisites**  
✅ Java 17+  
✅ MySQL Database  

#### ➤ **Steps to Run Backend**
```bash
cd backend
mvn clean install
mvn spring-boot:run
```
🚀 The backend will be available at: **http://localhost:8080**

#### ➤ **Configure MySQL Database**
- Open `application.properties` in `src/main/resources/`
- Update MySQL credentials:  
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ems_db
spring.datasource.username=root
spring.datasource.password=yourpassword
```

---

### ⚡ **3. Frontend Setup (React.js)**
#### ➤ **Pre-requisites**  
✅ Node.js 16+  
✅ npm or yarn  

#### ➤ **Steps to Run Frontend**
```bash
cd frontend
npm install
npm run dev
```
🚀 The frontend will be available at: **http://localhost:5173**

---

## 🔐 **Authentication & Role Management**
1. Users must **log in** to access the system.  
2. Admins have **extra privileges** like managing user roles.  
3. JWT tokens are used to **authenticate API requests** securely.  

📌 **Default User Credentials for Testing:**  
```bash
Admin: admin@example.com / password  
User: user@example.com / password  
```

---

## 📂 **Project Structure**

```
/employee-management-system
├── backend
│   ├── src/main/java/com/ems
│   │   ├── controller/      # API Controllers
│   │   ├── model/           # Entity Models
│   │   ├── repository/      # Data Access Layer
│   │   ├── service/         # Business Logic
│   │   ├── security/        # JWT & Security Config
│   ├── resources/
│   │   ├── application.properties  # DB Configurations
│   ├── pom.xml  # Maven Dependencies
│   ├── EmployeeManagementSystemApplication.java
│
├── frontend
│   ├── src/
│   │   ├── components/       # UI Components
│   │   ├── pages/            # Page Views
│   │   ├── services/         # API Services (Axios)
│   ├── package.json  # Dependencies
│   ├── vite.config.js  # Vite Config
│   ├── App.js  # Main App Component
```

---

## 🔥 **API Endpoints**  

### **🔐 Authentication**
| Method | Endpoint | Description |
|--------|---------|------------|
| `POST` | `/api/auth/login` | User Login (Returns JWT Token) |
| `POST` | `/api/auth/register` | Register a New User |

### **👨‍💼 Employee Management**
| Method | Endpoint | Description |
|--------|---------|------------|
| `GET` | `/api/employees` | Fetch All Employees |
| `POST` | `/api/employees` | Add New Employee |
| `PUT` | `/api/employees/{id}` | Update Employee |
| `DELETE` | `/api/employees/{id}` | Delete Employee |

---

## 📜 **License**
This project is **MIT Licensed**. Feel free to use and modify it.  

---

## 💡 **Contributions**
Want to improve this project? **Fork and submit a PR!**  
Let's build something amazing together!   

👨‍💻 **Author:** Vetri K  
🔗 **GitHub:** [Vetri](https://github.com/Vetri75)  
📧 **Contact:** kvetri137@gmail.com  

