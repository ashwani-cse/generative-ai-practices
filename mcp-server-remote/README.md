# MCP Server (Web / SSE)

This project is a **remote MCP (Model Context Protocol) server** built using **Spring Boot + Spring AI**, exposed over **HTTP using SSE (Server‑Sent Events)**. It allows MCP clients (like MCP Inspector or LLM-based agents) to discover and invoke tools over the network.

This README explains **what this server is**, **why it exists**, and **how you can test it locally on your machine** — without diving into code.

---

## 1️⃣ What is this MCP Server?

In simple terms:

> This is a **tool server** that speaks the MCP protocol over HTTP.

Instead of running as a local process (stdio-based MCP), this server:

* Runs as a **normal Spring Boot web application**
* Listens on **[http://localhost:8090](http://localhost:8090)**
* Uses **SSE** to maintain a streaming connection with MCP clients
* Exposes **tools** such as:

  * `createTicket`
  * `getTicketStatus`

Any MCP-compatible client can connect to it remotely and call these tools.

---

## 2️⃣ Why does this exist?

This server demonstrates how MCP can be used in **real enterprise systems**, where:

* Tools live in backend services
* The service must be reachable over HTTP
* Authentication, logging, persistence, and monitoring are required

Typical real‑world use cases:

* LLM creating support tickets
* AI querying internal systems
* Agent workflows calling backend APIs
* Centralized tool servers shared by many clients

---

## 3️⃣ How this MCP Server works (Conceptually)

High‑level flow:

1. MCP client connects to the server over **HTTP + SSE**
2. Server sends its **capabilities** (tools, metadata)
3. Client lists available tools
4. Client invokes a tool with input
5. Server executes business logic and returns result

This all happens **over a single SSE connection**.

---

## 4️⃣ Transport Used: SSE (Server‑Sent Events)

* SSE keeps a **long‑lived HTTP connection** open
* Server streams MCP responses back to the client
* Works well for:

  * Tool invocation
  * Streaming responses
  * Remote connectivity

This is why MCP Inspector shows:

* Transport Type: **SSE**
* URL: `http://localhost:8090/sse`

---

## 5️⃣ How to test this MCP Server on your machine

### ✅ Prerequisites

Make sure you have:

* Java installed
* Maven installed
* MCP Inspector available (local or browser‑based)

---

### ✅ Step 1: Start the MCP Server

Run the Spring Boot application normally.

Once started successfully, the server will:

* Listen on **port 8090**
* Expose MCP over `/sse`
* Log startup information in console

You can confirm the server is running if:

* No startup errors appear
* Port `8090` is in use

---

### ✅ Step 2: Open MCP Inspector

Open MCP Inspector in your browser.

Configure it as follows:

* **Transport Type:** SSE
* **URL:** `http://localhost:8090/sse`
* **Connection Type:** Via Proxy

Click **Reconnect**.

If successful, you will see:

* Status: **Connected**
* Server name displayed
* MCP version shown

---

### ✅ Step 3: Initialize the MCP Session

In MCP Inspector:

* Click **Initialize**
* This establishes the MCP handshake

You should see the initialize request appear in **History**.

---

### ✅ Step 4: Discover Tools

Go to the **Tools** tab and click **List Tools**.

Expected result:

* `createTicket`
* `getTicketStatus`

This confirms:

✔ MCP handshake works
✔ Server capabilities are exposed

---

### ✅ Step 5: Invoke a Tool

Example flow:

* Select `createTicket`
* Provide required fields (issue, username)
* Execute the tool

Then:

* Server processes the request
* Response is streamed back
* Entry appears in **History**

---

## 6️⃣ How this differs from stdio MCP

| Aspect     | stdio MCP         | Web / SSE MCP   |
| ---------- | ----------------- | --------------- |
| Runs as    | Local process     | HTTP service    |
| Connection | stdin/stdout      | SSE             |
| Deployment | Developer machine | Server / Cloud  |
| Scaling    | ❌                 | ✅               |
| Security   | Minimal           | Full HTTP stack |

This project represents **production‑style MCP usage**.

---

## 7️⃣ When should you use this approach?

Use Web/SSE MCP when:

* Tools must be remotely accessible
* Multiple clients share the same tool server
* You want logging, auth, metrics
* MCP is part of a microservices architecture

---

## 8️⃣ Summary

* This is a **remote MCP server** using HTTP + SSE
* It exposes tools over a network endpoint
* MCP Inspector is used to test it locally
* This model scales to real enterprise systems

If you already understand stdio MCP, this project shows the **next level: MCP as a backend service**.
