# AttendEase — Student Attendance Management System

Full-stack educational demonstration using **Java 21, Spring Boot 4.1.2, Maven, H2, HTML/CSS/JavaScript, Git, Jenkins, Docker, Kubernetes**.

## Run locally on macOS

1. Install Java 21 and Maven.
2. Open this folder in VS Code or Terminal.
3. Run `mvn spring-boot:run`.
4. Open http://localhost:8081.
5. Admin: add students, faculty and subjects. Faculty: select subject/date and mark attendance. Student: enter a roll number to see percentages.

**Important:** Stop the existing attendance app before starting this project. A second instance using the same file-based H2 database will fail with a database lock. This downloaded project uses its *own* `data` folder. Do not copy over your old database without a backup.

## Build and Docker

```bash
mvn clean package
docker build -t attendance-system:latest .
docker run --rm -p 8081:8081 -e DB_URL='jdbc:h2:mem:attendance;DB_CLOSE_DELAY=-1' attendance-system:latest
```

The Docker command above uses an **in-memory database**; data is lost when the container stops. For persistent Docker data, use a volume and `DB_URL=jdbc:h2:file:/app/data/attendancedb`.

## Kubernetes (local development)

Build the image in the Docker environment used by your cluster, then:

```bash
kubectl apply -f kubernetes/
kubectl port-forward service/attendance-service 8081:8081
```

Open http://localhost:8081. The example deployment uses an **in-memory H2 database** for a single replica, so Kubernetes restarts erase records. For persistent production deployments, use a managed database and persistent storage.

## Jenkins

Create a Pipeline job connected to the Git repository containing this project and choose **Pipeline script from SCM**, script path `Jenkinsfile`. The Jenkins agent needs Java 21, Maven, Docker, and permission to run Docker. The pipeline checks out, builds, tests, and builds the image. It **does not automatically deploy** to Kubernetes; deployment requires cluster access and an image registry.

## GitHub

```bash
git init
git add .
git commit -m "Initial attendance system"
```

Create a repository on GitHub, then add its URL as a remote and push your branch.

## Limitations / security

This is a **coursework demonstration**, not a production-ready school system. Role pages are not password protected; anyone who can access the app can use Admin/Faculty endpoints. Add Spring Security, role-based authorization, authentication, input validation, audit logs, tests, and a production-grade database before real student use. Do not enter real student personal data in an unsecured deployment.
