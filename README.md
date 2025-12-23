## 📦 Dependências do Projeto

Este microserviço utiliza o ecossistema Spring Boot e Spring Cloud. Abaixo estão listadas as dependências principais incluídas no `pom.xml`:

### 🚀 Core & Web
- **Spring Boot Starter Web (MVC)**
    - `groupId: org.springframework.boot`
    - `artifactId: spring-boot-starter-webmvc`
- **Spring Boot Starter Validation**
    - `groupId: org.springframework.boot`
    - `artifactId: spring-boot-starter-validation`
- **Spring Boot DevTools** (Optional/Runtime)
    - `groupId: org.springframework.boot`
    - `artifactId: spring-boot-devtools`
- **Lombok** (Optional)
    - `groupId: org.projectlombok`
    - `artifactId: lombok`

### 🗄️ Persistência & Migração de Dados
- **Spring Data JPA**
    - `groupId: org.springframework.boot`
    - `artifactId: spring-boot-starter-data-jpa`
- **MySQL Connector/J** (Runtime)
    - `groupId: com.mysql`
    - `artifactId: mysql-connector-j`
- **Flyway Migration**
    - `groupId: org.springframework.boot`
    - `artifactId: spring-boot-starter-flyway`
- **Flyway MySQL Extension**
    - `groupId: org.flywaydb`
    - `artifactId: flyway-mysql`

### ☁️ Spring Cloud & Resiliência
- **Eureka Discovery Client**
    - `groupId: org.springframework.cloud`
    - `artifactId: spring-cloud-starter-netflix-eureka-client`
- **OpenFeign**
    - `groupId: org.springframework.cloud`
    - `artifactId: spring-cloud-starter-openfeign`
- **Resilience4j (Circuit Breaker)**
    - `groupId: org.springframework.cloud`
    - `artifactId: spring-cloud-starter-circuitbreaker-resilience4j`
- **Spring Boot Actuator**
    - `groupId: org.springframework.boot`
    - `artifactId: spring-boot-starter-actuator`

### 🧪 Testes (Scope: test)
- **Data JPA Test:** `org.springframework.boot:spring-boot-starter-data-jpa-test`
- **Flyway Test:** `org.springframework.boot:spring-boot-starter-flyway-test`
- **Validation Test:** `org.springframework.boot:spring-boot-starter-validation-test`
- **WebMVC Test:** `org.springframework.boot:spring-boot-starter-webmvc-test`
- **Actuator Test:** `org.springframework.boot:spring-boot-starter-actuator-test`

---

### ⚙️ Versões de Referência
- **Java:** 25
- **Spring Boot:** 4.0.0
- **Spring Cloud:** 2025.1.0