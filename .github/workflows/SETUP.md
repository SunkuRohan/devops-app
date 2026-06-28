# GitHub Actions CI/CD Pipeline — Setup Guide

## What this pipeline does

A single workflow (`cicd-pipeline.yml`) that ties together every phase of this project:

```
Build & Test (Gradle)
      ↓
Docker Build & Push to ECR
      ↓
Terraform Apply (provision EKS/VPC/ECR)   [optional, toggle on trigger]
      ↓
Deploy to EKS (kubectl apply + rollout)
      ↓
Health check via Load Balancer
      ↓
Terraform Destroy (cleanup)                [optional, toggle on trigger]
```

It's triggered **manually** (`workflow_dispatch`) rather than on every push — this gives you full control over when AWS costs are incurred, since EKS + Load Balancer cost money while running.

## Required GitHub Secrets

Go to your repo → **Settings → Secrets and variables → Actions → New repository secret**

| Secret Name | Value |
|---|---|
| `AWS_ACCESS_KEY_ID` | Your IAM user's access key |
| `AWS_SECRET_ACCESS_KEY` | Your IAM user's secret key |

**Never commit these to your repo** — GitHub Secrets are encrypted and only injected at runtime, masked in logs automatically.

## How to run the pipeline

1. Go to your repo on GitHub → **Actions** tab
2. Select **CI/CD Pipeline - devops-app** from the left sidebar
3. Click **Run workflow**
4. Choose:
   - `deploy_infra: true` — provisions Terraform infra + deploys to K8s
   - `deploy_infra: false` — only builds, tests, and pushes Docker image to ECR (no AWS infra cost)
   - `destroy_after: true` — automatically tears down everything at the end of a successful run (recommended given limited AWS credits)
   - `destroy_after: false` — leaves infra running (use only if you want to keep testing manually afterward)
5. Click the green **Run workflow** button

## Monitoring a run

- Click into the running workflow to see live logs for each stage
- Each job (Build & Test, Docker Build & Push, Terraform Apply, Deploy to EKS, Cleanup) runs as a separate visual block
- If a stage fails, later stages are skipped automatically (`needs:` dependencies)

## Cost safety net

This pipeline defaults to `destroy_after: true` — meaning even if you forget, the infra is torn down automatically after a successful deployment + health check. This mirrors a recommended real-world pattern for ephemeral dev/test environments.

## Image tagging strategy

Each run tags the Docker image with the **short Git commit SHA** (e.g., `devops-app:a1b2c3d4`) — this gives every deployment a unique, traceable version instead of always overwriting `latest`. The `latest` tag is also pushed for convenience, but Kubernetes deploys the SHA-tagged version specifically.

## If a run fails midway

Since Terraform state is *not* preserved between GitHub Actions runs by default (no remote backend configured), if a pipeline run fails after `terraform apply` but before `destroy`, you must manually run `terraform destroy` locally to avoid leftover AWS charges — same as we've been doing manually so far.

**For a more production-grade setup**, Terraform state would be stored remotely (S3 + DynamoDB lock) so any run — local or CI — shares the same state. This is a good thing to mention in interviews as a "next improvement" you're aware of, even if not implemented here.
