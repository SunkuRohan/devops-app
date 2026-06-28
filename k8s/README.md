# Kubernetes — devops-app

Deploys the devops-app container to AWS EKS.

## Files

| File | Purpose |
|---|---|
| `deployment.yaml` | Defines the Deployment — 2 replicas, rolling updates, resource limits, health probes |
| `service.yaml` | Exposes the Deployment via a LoadBalancer (creates an AWS ELB) |

## Deploy

```bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```

## Verify

```bash
kubectl get pods                  # See running pods
kubectl get deployments           # See deployment status
kubectl get svc                   # See service + external LoadBalancer IP/hostname
kubectl describe pod <pod-name>   # Detailed pod info — useful for debugging
kubectl logs <pod-name>           # View container logs
```

## Test rolling updates / self-healing

```bash
# Delete a pod and watch Kubernetes recreate it automatically
kubectl delete pod <pod-name>
kubectl get pods -w

# Simulate a new release
kubectl set image deployment/devops-app devops-app=<new-image>:tag
kubectl rollout status deployment/devops-app
```

## Cleanup

```bash
kubectl delete -f service.yaml
kubectl delete -f deployment.yaml
```

**Important:** delete the Service first — it provisions a real AWS Load Balancer that keeps costing money even after the Deployment is gone. Then run `terraform destroy` once verification is complete.
