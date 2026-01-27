# MCP Inspector

## What is the MCP Inspector?

The **MCP Inspector** is an interactive **developer and debugging tool** for **Model Context Protocol (MCP)** servers.  
It helps developers **inspect, test, and validate MCP capabilities** exposed by a server before integrating them into applications like Spring AI, agent frameworks, or desktop clients.

You can think of the MCP Inspector as **Postman for MCP capabilities**.

It allows you to:
- Discover available capabilities (tools, resources, prompts)
- Invoke capabilities manually
- Inspect schemas, inputs, and outputs
- Debug MCP server behavior in real time

---

## Why Do We Use the MCP Inspector?

The MCP Inspector is used to:

- 🔍 Explore which **capabilities** a server exposes
- 🧪 Manually test and invoke capabilities
- 📜 Validate request/response schemas
- 🐞 Debug errors and unexpected behavior
- 🧠 Understand capability negotiation between client and server
- 📦 Verify server readiness before client integration

Using the Inspector reduces surprises when integrating MCP servers with:
- Spring AI
- Agentic workflows
- Production systems

---

## Prerequisites (Important)

### Node.js Must Be Installed Locally

The MCP Inspector is executed using **`npx`**, which means:

> **Node.js must be installed on the local machine**

Without Node.js:
- `npx` cannot run
- MCP Inspector will not start

---

### Check if Node.js Is Installed (macOS)

Run the following commands in the terminal:

```bash
node -v
```

