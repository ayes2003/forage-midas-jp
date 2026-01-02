# Midas Core - Financial Transaction Engine

### 🚀 Overview
**Implementation of key features** for a real-time banking transaction engine as part of the **JPMorgan Chase & Co. Software Engineering Virtual Experience**. I was responsible for building the Kafka event processing pipeline, integrating the database for persistence, and connecting external microservices to handle high-volume financial data.

### 🛠️ Tech Stack
* **Java & Spring Boot**: Core application logic and REST API development.
* **Apache Kafka:** Handled real-time streaming of transaction data.
* **H2 Database:** In-memory SQL database for managing user accounts and transaction persistence.
* **Spring Data JPA:** ORM for seamless database interaction.
* **Microservices:** Integrated external "Incentive API" using `RestTemplate`.

### ⚡ Key Features
* **Kafka Consumer:** Implemented a listener to ingest financial transaction messages in real-time.
* **Data Persistence:** Designed `User` and `Transaction` entities to store valid records in an H2 database.
* **External Integration:** Connected to a separate Incentive API to calculate and apply bonuses to eligible transactions.
* **REST API:** Built a `/balance` endpoint to allow users to query their real-time account balance.
* **Error Handling:** Solved complex deserialization and dependency injection challenges during implementation.

### 📂 Project Structure
* `com.jpmc.midascore.component`: Contains the **KafkaListener** and **BalanceController**.
* `com.jpmc.midascore.entity`: Database entities (`UserRecord`, `TransactionRecord`).
* `com.jpmc.midascore.repository`: JPA repositories for database access.
* `com.jpmc.midascore.foundation`: Data models like `Transaction` and `Balance`.

---
*This project was completed as part of the Forage JPMC Software Engineering Virtual Experience.*
