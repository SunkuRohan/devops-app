# devops-app

A simple Spring Boot REST API built as the foundation for a full DevOps pipeline project covering GitHub Actions, Docker, Kubernetes, Terraform, Ansible, and AWS.

## Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/health` | Health check — used by K8s liveness/readiness probes |
| GET | `/api/info` | App metadata |
| GET | `/api/greet?name=Rohan` | Sample greeting endpoint |
| GET | `/actuator/health` | Spring Actuator health (detailed) |

## Run Locally

**Prerequisites:** Java 17, Gradle

```bash
./gradlew clean build
./gradlew bootRun
```

Visit: http://localhost:8080/api/health

## Run with Docker

```bash
docker build -t devops-app:1.0.0 .
docker run -p 8080:8080 devops-app:1.0.0
```

## Run Tests

```bash
./gradlew test
```

## Environment Variables

Override these in Docker/Kubernetes:

| Variable | Default | Description |
|----------|---------|-------------|
| `APP_VERSION` | `1.0.0` | App version shown in health response |
| `APP_ENVIRONMENT` | `local` | Environment name (local/dev/qa/prod) |
| `SERVER_PORT` | `8080` | Port the app listens on |

## Project Roadmap

- [x] Phase 1 — Spring Boot REST API
- [ ] Phase 2 — Dockerize & push to AWS ECR
- [ ] Phase 3 — Provision AWS infra with Terraform (VPC, EKS, ECR)
- [ ] Phase 4 — Configure EC2 with Ansible
- [ ] Phase 5 — Deploy to Kubernetes (EKS)
- [ ] Phase 6 — Full CI/CD pipeline in GitHub Actions
