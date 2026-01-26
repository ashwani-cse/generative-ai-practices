# Known Errors :-

## ❌ npm EACCES / Permission Denied while Starting MCP Server

### Context
This issue occurred while running a **Spring AI application using MCP (Model Context Protocol)**.
During Spring Boot startup, the MCP Node.js server is initialized, which requires npm to access its cache directory.

---

### Error Symptoms

- Spring Boot application fails during startup
- MCP server does not initialize
- ApplicationContext fails to load
- Logs show npm permission errors such as:

```text
npm ERR! code EACCES
npm ERR! syscall mkdir
npm ERR! path /Users/ashwanikumar/.npm/_cacache/index-v5/8a/42
npm ERR! errno EACCES
npm ERR! Your cache folder contains root-owned files, due to a bug in
npm ERR! previous versions of npm which has since been addressed.
npm ERR! To permanently fix this problem, please run:
npm ERR!   sudo chown -R 501:20 /Users/ashwanikumar/.npm
```

### Root Cause
At some point, npm commands were executed using sudo, which caused the npm cache directory 
to be owned by root instead of the logged-in user.
```text
~/.npm → owned by root
```
When Spring AI attempted to start the MCP Node.js process, npm required write access to the
cache directory, which was denied due to incorrect ownership.

### Resolution Steps
1. **Change Ownership of npm Cache Directory**:
   Run the following command to change the ownership of the npm cache directory back to your user:
    ```bash
    sudo chown -R 501:20 /Users/ashwanikumar/.npm
    ```
501:20 represents the default user and staff group on macOS.
After fixing permissions, restart the application.