#!/bin/bash
# =====================================================
# ATMOS Kubernetes — Full Deployment Script
# =====================================================
# Usage: bash deploy.sh
# Prerequisites: kubectl configured, cluster running
# =====================================================

set -e

NAMESPACE="atmos"
echo "=========================================="
echo "  ATMOS Kubernetes Deployment"
echo "=========================================="

# --- Step 1: Foundation ---
echo ""
echo "[1/8] Creating namespace, secrets & configmaps..."
kubectl apply -f 00-namespace.yml
kubectl apply -f 01-secrets.yml
kubectl apply -f 02-configmaps.yml
echo "  ✅ Foundation ready"

# --- Step 2: Zookeeper ---
echo ""
echo "[2/8] Deploying Zookeeper..."
kubectl apply -f infrastructure/zookeeper.yml
echo "  Waiting for Zookeeper to be ready..."
kubectl wait --for=condition=ready pod -l app=zookeeper -n $NAMESPACE --timeout=120s
echo "  ✅ Zookeeper ready"

# --- Step 3: Kafka ---
echo ""
echo "[3/8] Deploying Kafka & Kafka UI..."
kubectl apply -f infrastructure/kafka.yml
echo "  Waiting for Kafka to be ready..."
kubectl wait --for=condition=ready pod -l app=kafka -n $NAMESPACE --timeout=180s
kubectl apply -f infrastructure/kafka-ui.yml
echo "  ✅ Kafka ready"

# --- Step 4: ELK Stack ---
echo ""
echo "[4/8] Deploying ELK Stack (Elasticsearch → Logstash → Kibana)..."
kubectl apply -f infrastructure/elasticsearch.yml
echo "  Waiting for Elasticsearch to be ready..."
kubectl wait --for=condition=ready pod -l app=elasticsearch -n $NAMESPACE --timeout=180s
kubectl apply -f infrastructure/logstash.yml
kubectl apply -f infrastructure/kibana.yml
echo "  ✅ ELK Stack ready"

# --- Step 5: Observability ---
echo ""
echo "[5/8] Deploying Zipkin, Prometheus & Grafana..."
kubectl apply -f infrastructure/zipkin.yml
kubectl apply -f infrastructure/prometheus.yml
kubectl apply -f infrastructure/grafana.yml
echo "  ✅ Observability stack ready"

# --- Step 6: Service Registry ---
echo ""
echo "[6/8] Deploying Service Registry (Eureka)..."
kubectl apply -f services/service-registry.yml
echo "  Waiting for Eureka to be ready..."
kubectl wait --for=condition=ready pod -l app=service-registry -n $NAMESPACE --timeout=180s
echo "  ✅ Service Registry ready"

# --- Step 7: Application Services ---
echo ""
echo "[7/8] Deploying application services..."
kubectl apply -f services/auth-service.yml
kubectl apply -f services/weather-service.yml
kubectl apply -f services/astronomy-service.yml
kubectl apply -f services/news-service.yml
kubectl apply -f services/notification-service.yml

echo "  Waiting for all app services to be ready..."
kubectl wait --for=condition=ready pod -l app=auth-service -n $NAMESPACE --timeout=180s
kubectl wait --for=condition=ready pod -l app=weather-service -n $NAMESPACE --timeout=180s
kubectl wait --for=condition=ready pod -l app=astronomy-service -n $NAMESPACE --timeout=180s
kubectl wait --for=condition=ready pod -l app=news-service -n $NAMESPACE --timeout=180s
kubectl wait --for=condition=ready pod -l app=notification-service -n $NAMESPACE --timeout=180s
echo "  ✅ Application services ready"

# --- Step 8: Gateway ---
echo ""
echo "[8/8] Deploying Gateway Service..."
kubectl apply -f services/gateway-service.yml
echo "  Waiting for Gateway to be ready..."
kubectl wait --for=condition=ready pod -l app=gateway-service -n $NAMESPACE --timeout=180s
echo "  ✅ Gateway ready"

# --- Summary ---
echo ""
echo "=========================================="
echo "  🚀 ATMOS Deployment Complete!"
echo "=========================================="
echo ""
kubectl get pods -n $NAMESPACE
echo ""
echo "--- Services ---"
kubectl get svc -n $NAMESPACE
echo ""
echo "--- Quick Access (port-forward) ---"
echo "  Gateway:    kubectl port-forward svc/gateway-service 8080:8080 -n $NAMESPACE"
echo "  Grafana:    kubectl port-forward svc/grafana 3000:3000 -n $NAMESPACE"
echo "  Kibana:     kubectl port-forward svc/kibana 5601:5601 -n $NAMESPACE"
echo "  Zipkin:     kubectl port-forward svc/zipkin 9411:9411 -n $NAMESPACE"
echo "  Prometheus: kubectl port-forward svc/prometheus 9090:9090 -n $NAMESPACE"
echo "  Kafka UI:   kubectl port-forward svc/kafka-ui 8989:8989 -n $NAMESPACE"
echo "  Eureka:     kubectl port-forward svc/service-registry 8761:8761 -n $NAMESPACE"
