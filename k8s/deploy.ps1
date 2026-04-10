# =====================================================
# ATMOS Kubernetes — Full Deployment Script (PowerShell)
# =====================================================
# Usage: .\deploy.ps1
# Prerequisites: kubectl configured, cluster running
# =====================================================

$ErrorActionPreference = "Stop"
$NAMESPACE = "atmos"

Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "  ATMOS Kubernetes Deployment" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan

# --- Step 1: Foundation ---
Write-Host ""
Write-Host "[1/8] Creating namespace, secrets & configmaps..." -ForegroundColor Yellow
kubectl apply -f 00-namespace.yml
kubectl apply -f 01-secrets.yml
kubectl apply -f 02-configmaps.yml
Write-Host "  ✅ Foundation ready" -ForegroundColor Green

# --- Step 2: Zookeeper ---
Write-Host ""
Write-Host "[2/8] Deploying Zookeeper..." -ForegroundColor Yellow
kubectl apply -f infrastructure/zookeeper.yml
Write-Host "  Waiting for Zookeeper to be ready..."
kubectl wait --for=condition=ready pod -l app=zookeeper -n $NAMESPACE --timeout=120s
Write-Host "  ✅ Zookeeper ready" -ForegroundColor Green

# --- Step 3: Kafka ---
Write-Host ""
Write-Host "[3/8] Deploying Kafka & Kafka UI..." -ForegroundColor Yellow
kubectl apply -f infrastructure/kafka.yml
Write-Host "  Waiting for Kafka to be ready..."
kubectl wait --for=condition=ready pod -l app=kafka -n $NAMESPACE --timeout=180s
kubectl apply -f infrastructure/kafka-ui.yml
Write-Host "  ✅ Kafka ready" -ForegroundColor Green

# --- Step 4: ELK Stack ---
Write-Host ""
Write-Host "[4/8] Deploying ELK Stack (Elasticsearch -> Logstash -> Kibana)..." -ForegroundColor Yellow
kubectl apply -f infrastructure/elasticsearch.yml
Write-Host "  Waiting for Elasticsearch to be ready..."
kubectl wait --for=condition=ready pod -l app=elasticsearch -n $NAMESPACE --timeout=180s
kubectl apply -f infrastructure/logstash.yml
kubectl apply -f infrastructure/kibana.yml
Write-Host "  ✅ ELK Stack ready" -ForegroundColor Green

# --- Step 5: Observability ---
Write-Host ""
Write-Host "[5/8] Deploying Zipkin, Prometheus & Grafana..." -ForegroundColor Yellow
kubectl apply -f infrastructure/zipkin.yml
kubectl apply -f infrastructure/prometheus.yml
kubectl apply -f infrastructure/grafana.yml
Write-Host "  ✅ Observability stack ready" -ForegroundColor Green

# --- Step 6: Service Registry ---
Write-Host ""
Write-Host "[6/8] Deploying Service Registry (Eureka)..." -ForegroundColor Yellow
kubectl apply -f services/service-registry.yml
Write-Host "  Waiting for Eureka to be ready..."
kubectl wait --for=condition=ready pod -l app=service-registry -n $NAMESPACE --timeout=180s
Write-Host "  ✅ Service Registry ready" -ForegroundColor Green

# --- Step 7: Application Services ---
Write-Host ""
Write-Host "[7/8] Deploying application services..." -ForegroundColor Yellow
kubectl apply -f services/auth-service.yml
kubectl apply -f services/weather-service.yml
kubectl apply -f services/astronomy-service.yml
kubectl apply -f services/news-service.yml
kubectl apply -f services/notification-service.yml

Write-Host "  Waiting for all app services to be ready..."
kubectl wait --for=condition=ready pod -l app=auth-service -n $NAMESPACE --timeout=180s
kubectl wait --for=condition=ready pod -l app=weather-service -n $NAMESPACE --timeout=180s
kubectl wait --for=condition=ready pod -l app=astronomy-service -n $NAMESPACE --timeout=180s
kubectl wait --for=condition=ready pod -l app=news-service -n $NAMESPACE --timeout=180s
kubectl wait --for=condition=ready pod -l app=notification-service -n $NAMESPACE --timeout=180s
Write-Host "  ✅ Application services ready" -ForegroundColor Green

# --- Step 8: Gateway ---
Write-Host ""
Write-Host "[8/8] Deploying Gateway Service..." -ForegroundColor Yellow
kubectl apply -f services/gateway-service.yml
Write-Host "  Waiting for Gateway to be ready..."
kubectl wait --for=condition=ready pod -l app=gateway-service -n $NAMESPACE --timeout=180s
Write-Host "  ✅ Gateway ready" -ForegroundColor Green

# --- Summary ---
Write-Host ""
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host "  🚀 ATMOS Deployment Complete!" -ForegroundColor Cyan
Write-Host "==========================================" -ForegroundColor Cyan
Write-Host ""
kubectl get pods -n $NAMESPACE
Write-Host ""
Write-Host "--- Services ---" -ForegroundColor Yellow
kubectl get svc -n $NAMESPACE
Write-Host ""
Write-Host "--- Quick Access (port-forward) ---" -ForegroundColor Yellow
Write-Host "  Gateway:    kubectl port-forward svc/gateway-service 8080:8080 -n $NAMESPACE"
Write-Host "  Grafana:    kubectl port-forward svc/grafana 3000:3000 -n $NAMESPACE"
Write-Host "  Kibana:     kubectl port-forward svc/kibana 5601:5601 -n $NAMESPACE"
Write-Host "  Zipkin:     kubectl port-forward svc/zipkin 9411:9411 -n $NAMESPACE"
Write-Host "  Prometheus: kubectl port-forward svc/prometheus 9090:9090 -n $NAMESPACE"
Write-Host "  Kafka UI:   kubectl port-forward svc/kafka-ui 8989:8989 -n $NAMESPACE"
Write-Host "  Eureka:     kubectl port-forward svc/service-registry 8761:8761 -n $NAMESPACE"
