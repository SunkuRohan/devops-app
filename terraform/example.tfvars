# Copy this to terraform.tfvars and customize
aws_region   = "ap-south-1"
project_name = "devops-app"

common_tags = {
  Project     = "devops-app"
  Environment = "dev"
  ManagedBy   = "terraform"
}
