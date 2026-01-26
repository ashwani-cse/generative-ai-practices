# MCP Servers Testing with NPX (Model Context Protocol)

This project documents testing of **Model Context Protocol (MCP)** servers using `npx` and **standard I/O (stdio)** communication.

In MCP, **everything is a capability**.  
A capability is a clearly defined, discoverable, and scoped operation that an AI model can invoke safely.

---

## 1️⃣ What is Model Context Protocol (MCP)?

**Model Context Protocol (MCP)** is a standard that defines how AI models interact with external systems (tools, data, services) in a **structured, secure, and predictable** way.

Instead of letting models call arbitrary code or APIs, MCP introduces:

- Capability-based access
- Explicit contracts
- Sandboxed execution
- Transport-agnostic communication (stdio, HTTP, etc.)

---

## 2️⃣ Core MCP Concepts (Must Know)

### 🔹 Capability
A **capability** is a single unit of functionality exposed to a model.

Examples:
- Read a file
- Write a file
- List a directory
- Fetch a URL
- Query memory
- Get current time

> In MCP, the model never calls “the system”.  
> It only invokes **capabilities**.

---

### 🔹 MCP Server
An **MCP server** is a process that **provides one or more capabilities**.

Examples:
- Filesystem server → filesystem capabilities
- Git server → git capabilities
- Memory server → memory capabilities

---

### 🔹 MCP Client
An **MCP client**:
- Starts MCP servers
- Discovers available capabilities
- Exposes them to the LLM
- Routes requests and responses

(Spring AI, Claude Desktop, custom agents, etc.)

---

### 🔹 Transport
How the client and server communicate.

Common transports:
- `stdio` (used here)
- HTTP
- WebSocket (future/optional)

---

## 3️⃣ Official MCP Links & Their Purpose

### 🔗 https://github.com/modelcontextprotocol
**Purpose**:
- Root organization for MCP
- Contains protocol specs, SDKs, tools, and design docs
- Defines how capabilities are modeled and exchanged

---

### 🔗 https://github.com/modelcontextprotocol/servers?tab=readme-ov-file
**Purpose**:
- Reference implementations of MCP servers
- Each server demonstrates a real capability
- Acts as examples and building blocks for integrations

---

### 🔗 https://github.com/modelcontextprotocol/servers/tree/main/src/filesystem
**Purpose**:
- Source code for the **filesystem capability server**
- Shows how filesystem operations are safely exposed as MCP capabilities
- Demonstrates sandboxing using allowed directories

---

## 4️⃣ MCP Capability Model (How Everything Fits)

```text
+----------------+        stdio        +----------------------+
|   MCP Client   | <----------------> |     MCP Server       |
|  (Spring AI)   |                    |   (Filesystem)       |
+----------------+                    +----------------------+
        |                                      |
        |   Capability Discovery               |
        |------------------------------------->|
        |                                      |
        |   Capability Invocation              |
        |------------------------------------->|
        |                                      |
        |   Capability Response                |
        |<-------------------------------------|
        |                                      |
+----------------+                    +----------------------+
|   LLM Model    |                    |   Filesystem Ops     |
+----------------+                    +----------------------+
```

Key points:
- Client does not assume capabilities
- Server explicitly declares them
- Model can only use what is declared

---

## 5️⃣ Filesystem Capability (via NPX)

The filesystem server exposes **filesystem capabilities** like:
- read_file
- write_file
- list_directory

These capabilities are **restricted** to allowed paths only.

### Configuration Example

```json
{
  "mcpServers": {
    "filesystem": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "/Users/username/Desktop",
        "/path/to/other/allowed/dir"
      ]
    }
  }
}
```
## 6️⃣ Configuration Explained (Capability Perspective)

- **command: npx**  
  Starts the capability server without global installation.

- **@modelcontextprotocol/server-filesystem**  
  Provides filesystem-related capabilities.

- **Allowed directories**  
  These define the **capability boundary**.

  - ✅ The model can access files inside these paths  
  - ❌ The model cannot access anything outside  

This enforces the **principle of least privilege**.

---

## 7️⃣ Capability Invocation Flow (Step-by-Step)

1. MCP client starts the filesystem server using `npx`
2. Server registers and exposes its capabilities
3. Client discovers the available capabilities
4. LLM selects the appropriate capability based on intent
5. Client invokes the capability over `stdio`
6. Server executes the operation within defined constraints
7. Result is returned to the client and then to the model

---

## 8️⃣ Why MCP is Better Than Direct Tool Calls

| Concern | Without MCP | With MCP |
|------|-------------|----------|
| Security | Model can overreach | Capabilities are tightly scoped |
| Discoverability | Implicit, undocumented | Explicit and discoverable |
| Auditing | Difficult | Structured and traceable |
| Isolation | Weak | Strong sandboxing |
| Production readiness | Risky | Safe by design |

---

## 9️⃣ Best Practices

- Treat MCP servers as **capability providers**, not generic services
- Scope capabilities as narrowly as possible
- Avoid running `npm` or `npx` with `sudo`
- Use `nvm` for Node.js version management
- Validate filesystem permissions before startup
- Externalize capability servers in production environments

---

## 🔟 Production Notes

Because MCP servers may start during application initialization:

- Any capability startup failure can fail the application
- Node.js or npm permission issues are critical failure points

Recommended approaches:
- Lazy or on-demand capability initialization
- Running MCP servers as separate, managed processes
- Implementing graceful degradation when capabilities are unavailable
