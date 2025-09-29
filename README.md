# 📄 DocuSmart

[![Spring Boot](https://img.shields.io/badge/SpringBoot-3.0+-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-DB-blue?logo=postgresql)](https://www.postgresql.org/)
[![Elasticsearch](https://img.shields.io/badge/Elasticsearch-Search-yellow?logo=elasticsearch)](https://www.elastic.co/elasticsearch/)
[![Kafka](https://img.shields.io/badge/Kafka-Event--Driven-black?logo=apachekafka)](https://kafka.apache.org/)
[![Spring AI](https://img.shields.io/badge/SpringAI-AI-purple?logo=spring)](https://spring.io/projects/spring-ai)
[![Keycloak](https://img.shields.io/badge/Keycloak-Auth-red?logo=keycloak)](https://www.keycloak.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](./LICENSE)

**DocuSmart** is a secure, intelligent, and event-driven **document management backend** built with **Spring Boot**.  
It combines **AI-powered document classification**, **full-text search & auto-suggestions**, **RBAC-based security via Keycloak**, and **real-time event streaming via Kafka** — all within a **monolithic architecture**.

---

## ✨ Features

- 🔐 **Authentication & Authorization**
  - **Keycloak** for centralized identity and access management  
  - Role-Based Access Control (RBAC) with `ADMIN`, `EDITOR`, `VIEWER`  

- 🤖 **AI-Enhanced Workflows**
  - **Document Classification** using **Spring AI**  
  - Automatic tagging, categorization, or summarization of documents  

- 🔎 **Search & Auto-Suggestions**
  - **Elasticsearch** for fast full-text search  
  - Auto-completion using Elasticsearch’s **completion suggester**  

- ⚡ **Event-Driven Processing**
  - **Apache Kafka** streams document events (`created`, `updated`, `deleted`)  
  - Downstream consumers handle analytics, notifications, and auditing  

- 🗄 **Reliable Persistence**
  - **PostgreSQL** for structured data storage  

- 🏗 **Monolithic Architecture**
  - Single codebase for easier deployment and management  

---

## 🏛️ Architecture

```mermaid
flowchart TD
    A[Client / Frontend] --> B["DocuSmart (Spring Boot)"]
    B --> C[(PostgreSQL)]
    B --> D[(Elasticsearch)]
    B --> E[(Kafka Topics / Consumers)]
    B --> F["Spring AI (Document Classification)"]
    B --> G["Keycloak (Authentication & RBAC)"]
