# MCP Inspector Runbook

## Overview

MCP Inspector is a local tool used to inspect and interact with the **Model Context Protocol (MCP)**. It allows developers to test and invoke various tools (e.g., GitHub actions) via a local proxy. This runbook describes how to start MCP Inspector and check GitHub tool capabilities.

---

## Prerequisites

* **Node.js** installed locally (v18+ recommended)
* **npx** command available
* Docker installed (if running tools that require containerized execution)
* GitHub Personal Access Token (PAT) if accessing GitHub tools
* Internet connection for downloading dependencies

---

## Step 1: Start MCP Inspector

1. Open your terminal.
2. Run the following command:

```bash
npx @modelcontextprotocol/inspector
```

3. You should see output similar to:

```
Starting MCP inspector...
Proxy server listening on localhost:6277
Session token: <session_token>
Use this token to authenticate requests or set DANGEROUSLY_OMIT_AUTH=true to disable auth
MCP Inspector is up and running at:
http://localhost:6274/?MCP_PROXY_AUTH_TOKEN=<session_token>
Opening browser...
```

**Notes:**

* `localhost:6274` is the web interface URL for MCP Inspector.
* The `MCP_PROXY_AUTH_TOKEN` token is required for authentication.

---

## Step 2: Open MCP Inspector Web UI

1. Copy the URL from the terminal:

```
http://localhost:6274/?MCP_PROXY_AUTH_TOKEN=<session_token>
```

2. Open it in your browser.
3. You should see the MCP Inspector dashboard with tabs: **Resources, Prompts, Tools, Tasks, Ping, Sampling, Elicitations, Roots, Auth, Metadata**

---

## Step 3: Verify GitHub Tool Capabilities

1. Navigate to the **Tools** tab.
2. Select the **Transport Type** as `STDIO`.
3. In the **Command** field, enter:

```
docker
```

4. In the **Arguments** field, provide your Docker run arguments if needed (e.g., GitHub PAT for tool access):

```
run -i --rm -e GITHUB_PERSONAL_ACCESS_TOKEN ghcr.io/github/github-mcp-server
```

5. Add environment variables required for your setup:

| Name                         | Value              |
| ---------------------------- | ------------------ |
| HOME                         | /Users/<username>  |
| LOGNAME                      | <username>         |
| PATH                         | /usr/local/bin:... |
| SHELL                        | /bin/zsh           |
| GITHUB_PERSONAL_ACCESS_TOKEN | <your_token>       |

6. Click **List Tools**. You should see all available GitHub tools, e.g.:

* `add_comment_to_pending_review`
* `add_issue_comment`
* `assign_copilot_to_issue`
* `create_branch`
* `create_or_update_file`
* `notifications/message`
* and more.

7. Click a tool to see details, including required parameters for execution.

---

## Step 4: Test a GitHub Tool

1. Select a tool (e.g., `add_issue_comment`).
2. Fill in the required fields:

| Field       | Description         |
| ----------- | ------------------- |
| body        | Text of the comment |
| owner       | Repository owner    |
| repo        | Repository name     |
| issueNumber | Issue number        |

3. Click **Execute** to invoke the tool.
4. Verify the output or GitHub repository for changes.

---

## Step 5: Troubleshooting

* **Error: write EPIPE**
  This typically occurs if the Docker container exits prematurely. Ensure:

    * Docker is running
    * GitHub token is valid
    * Environment variables are correctly set

* **Cannot list tools**

    * Make sure `STDIO` transport is selected
    * Verify MCP Inspector proxy is running on the correct port

---

## References

* [GitHub MCP Server](https://github.com/github/github-mcp-server)
* [MCP Inspector Documentation](https://www.npmjs.com/package/@modelcontextprotocol/inspector)
