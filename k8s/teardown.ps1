# ==========================================
#  ATMOS Kubernetes Teardown Script
# ==========================================

Write-Host "Stopping all ATMOS Backend Services..." -ForegroundColor Yellow

# 1. Stop all application microservices
Write-Host "Stopping Services..." -ForegroundColor Cyan
kubectl delete -f services/ --ignore-not-found=true

# 2. Stop all infrastructure databases & queues
Write-Host "Stopping Infrastructure..." -ForegroundColor Cyan
kubectl delete -f infrastructure/ --ignore-not-found=true

# Note: We do NOT delete the PVCs or the namespace so that your 
# Grafana dashboards and Elasticsearch data remain saved permanently on your hard drive!

Write-Host "==========================================" -ForegroundColor Green
Write-Host "  ✅ Microservices successfully stopped!  " -ForegroundColor Green
Write-Host "==========================================" -ForegroundColor Green
Write-Host "Note: Your persistent data (Dashboards) was kept safe. Run .\deploy.ps1 to start them back up."
