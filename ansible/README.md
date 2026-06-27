# Ansible — devops-app

Configuration management for the devops-app project. Practiced locally against WSL (`localhost`) to avoid AWS costs; the same playbooks work against a real EC2 instance by switching the inventory target.

## Files

| File | Purpose |
|---|---|
| `inventory.ini` | Defines target hosts (currently `localhost` for practice) |
| `ansible.cfg` | Ansible configuration — points to inventory, disables strict host key checking |
| `setup-server.yml` | Installs Docker, Java, creates app directory and env config |
| `deploy-app.yml` | Pulls/runs the devops-app Docker container, checks health endpoint |
| `requirements.yml` | Required Ansible collections (`community.docker`) |

## Usage

### 1. Install required collections
```bash
ansible-galaxy collection install -r requirements.yml
```

### 2. Test connectivity to target hosts
```bash
ansible all -m ping
```

### 3. Run server setup playbook
```bash
ansible-playbook setup-server.yml
```

### 4. Run app deployment playbook
```bash
ansible-playbook deploy-app.yml
```

### 5. Dry run (check mode — no changes made)
```bash
ansible-playbook setup-server.yml --check
```

## Switching to a real EC2 instance

Update `inventory.ini`:
```ini
[webservers]
<EC2_PUBLIC_IP> ansible_user=ubuntu ansible_ssh_private_key_file=~/.ssh/devops-app-key.pem
```

Then change `hosts: local` to `hosts: webservers` in both playbooks, and run the same commands.
