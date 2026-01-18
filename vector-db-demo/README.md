# Vector DB Demo – Spring AI with Qdrant

## Project Purpose

* Demonstrate **vector database integration** in a Spring Boot application
* Showcase **Spring AI abstractions** for embeddings and vector storage
* Provide a **clean base setup** for Retrieval-Augmented Generation (RAG)

---

## What This Project Does

* Uses **OpenAI** to generate vector embeddings
* Stores and manages embeddings in **Qdrant**
* Automatically initializes the vector collection on startup
* Connects Spring Boot to Qdrant using **Spring AI VectorStore** support
* Runs Qdrant locally using **Docker Compose**

---

## Technologies Used

* **Java 21**
* **Spring Boot 3.x**
* **Spring AI 1.1.x**
* **OpenAI (Embeddings)**
* **Qdrant Vector Database**
* **Docker Compose**

---

## Architecture Overview

* Spring Boot acts as the application runtime
* Spring AI manages:

    * Embedding generation
    * Vector store communication
* Qdrant serves as the persistent vector database
* Docker Compose ensures Qdrant availability during application startup

---

## Configuration Highlights

* AI and vector store settings are externalized via configuration files
* OpenAI API key is provided using environment variables
* Qdrant REST and gRPC ports are explicitly defined
* Vector collection is auto-created if not present

---

## Design Philosophy

* Infrastructure-first, minimal logic
* Focus on **AI + Vector DB integration**, not APIs or UI
* Production-aligned configuration patterns
* Easy to extend for real-world RAG use cases

---

## Intended Use Cases

* Learning Spring AI with vector databases
* Base template for RAG-enabled services
* Experimentation with embeddings and similarity search
* Reference project for AI-backed backend systems

---

## Out of Scope (By Design)

* No REST APIs for ingestion or search
* No UI or frontend layer
* No domain-specific business logic

---

## Extensibility

* Can be extended with:

    * Document ingestion pipelines
    * Semantic search APIs
    * Full RAG workflows
    * Alternate LLM providers (Azure OpenAI, Ollama)

---

## Summary

This project provides a **concise, production-style foundation** for building AI-powered applications using Spring Boot, Spring AI, and Qdrant. It focuses purely on the **core building blocks required for vector-based retrieval systems**.
