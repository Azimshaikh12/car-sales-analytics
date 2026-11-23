# Car Sales Analytics 🚗

A backend application built with Java and Spring Boot to manage car sales data, generate sales analytics, and explore AI-assisted data queries.

## 🚀 Features

- **Car Sales Management:** Store and manage car sales records.
- **Sales Analytics:** Retrieve yearly and monthly sales statistics.
- **REST APIs:** Expose endpoints for sales operations and analytics.
- **AI Integration:** Integrate Ollama with the application for AI-powered queries.
- **CSV Upload:** Support for uploading sales data files.
- **Exception Handling:** Centralized API exception handling.
- **Database Integration:** Persist sales data using MySQL.

## 🛠️ Tech Stack

| Technology | Purpose |
|---|---|
| Java | Backend development |
| Spring Boot | REST API development |
| Spring Data JPA / Hibernate | Database operations |
| MySQL | Relational database |
| Spring AI | AI integration |
| Ollama | Local AI model runtime |
| Maven | Dependency management |

## 🏗️ Project Structure

```text
carsales/
├── src/
│   ├── main/
│   │   ├── java/com/azim/carsales/
│   │   │   ├── commons/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── repositery/
│   │   │   ├── service/
│   │   │   └── CarsalesApplication.java
│   │   └── resources/
│   └── test/
├── pom.xml
├── mvnw
└── README.md
```

## ⚙️ Prerequisites

- Java (compatible with the version configured in `pom.xml`)
- MySQL
- Maven (optional, Maven Wrapper is included)
- Ollama

## 💻 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/Azimshaikh12/car-sales-analytics.git
cd car-sales-analytics
```

### 2. Configure the database

Create a MySQL database:

```sql
CREATE DATABASE carsales;
```

### 3. Configure application properties

Copy the example configuration:

```bash
cp src/main/resources/application.example.yaml src/main/resources/application.yaml
```

Update `application.yaml` with your local database credentials.

Never commit real passwords, API keys, or other secrets.

### 4. Configure Ollama

Install and run Ollama, then download the configured model:

```bash
ollama pull llama3
```

Ensure Ollama is accessible at:

```text
http://localhost:11434
```

### 5. Run the application

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```bash
mvnw.cmd spring-boot:run
```

## 🧪 Testing

Run the test suite:

```bash
./mvnw test
```

## 🔮 Future Improvements

- Add authentication and authorization.
- Improve sales analytics and reporting.
- Add interactive data visualizations.
- Enhance AI-assisted natural-language querying.
- Add API documentation using Swagger/OpenAPI.
- Add automated integration tests.

## 👨‍💻 Author

**Azim Shaikh**

- GitHub: [Azimshaikh12](https://github.com/Azimshaikh12)
- Project: [Car Sales Analytics](https://github.com/Azimshaikh12/car-sales-analytics)

---

⭐ If you find this project interesting, feel free to explore the repository!
