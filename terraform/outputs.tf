# ECR
output "ecr_repository_url" {
  description = "ECR repository URL — used for docker push and K8s manifests"
  value       = aws_ecr_repository.devops_app.repository_url
}

# VPC
output "vpc_id" {
  description = "VPC ID"
  value       = module.vpc.vpc_id
}

output "private_subnets" {
  description = "Private subnet IDs"
  value       = module.vpc.private_subnets
}

output "public_subnets" {
  description = "Public subnet IDs"
  value       = module.vpc.public_subnets
}

# EKS
output "eks_cluster_name" {
  description = "EKS cluster name — used for kubectl and GitHub Actions"
  value       = aws_eks_cluster.main.name
}

output "eks_cluster_endpoint" {
  description = "EKS cluster endpoint"
  value       = aws_eks_cluster.main.endpoint
}
