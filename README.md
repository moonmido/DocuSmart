# 📄 DocuSmart

[![Spring Boot](https://img.shields.io/badge/SpringBoot-3.0+-brightgreen?logo=springboot)](https://spring.io/projects/spring-boot)
[![PostgreSQL](https://img.shields.io/badge/PostgreSQL-DB-blue?logo=postgresql)](https://www.postgresql.org/)
[![Elasticsearch](https://img.shields.io/badge/Elasticsearch-Search-yellow?logo=elasticsearch)](https://www.elastic.co/elasticsearch/)
[![Kafka](https://img.shields.io/badge/Kafka-Event--Driven-black?logo=apachekafka)](https://kafka.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](./LICENSE)

**DocuSmart** is a secure, intelligent, and event-driven **document management backend** built with **Spring Boot**.  
It combines **AI-powered insights**, **full-text search & auto-suggestions**, **RBAC-based security**, and **real-time event streaming** via Kafka — all within a **monolithic architecture** for simplicity and maintainability.

---

## ✨ Features

- 🔐 **Authentication & Authorization**
  - JWT-based authentication
  - Role-Based Access Control (RBAC) with `ADMIN`, `EDITOR`, `VIEWER`

- 🤖 **AI-Enhanced Workflows**
  - Document analysis & recommendations
  - Tagging, summarization, or categorization support

- 🔎 **Search & Auto-Suggestions**
  - **Elasticsearch** integration for full-text search
  - Smart **auto-completion** via the `completion` suggester

- ⚡ **Event-Driven Processing**
  - **Apache Kafka** streams document events (`created`, `updated`, `deleted`)
  - Easy integration with downstream services (analytics, notifications, audit logs)

- 🗄 **Reliable Persistence**
  - **PostgreSQL** as the main relational database

- 🏗 **Monolithic Architecture**
  - Easier deployment and centralized module management

---

## 🏛️ Architecture

```mermaid
flowchart TD
    A[Client / Frontend] --> B["DocuSmart (Spring Boot)"]
    B --> C[(PostgreSQL)]
    B --> D[(Elasticsearch)]
    B --> E[(Kafka Topics / Consumers)]

