#!/bin/bash

# Quick start script for Management Apps
# This script starts all services in the correct order

set -e

echo "🚀 Starting Management Apps Microservices..."
echo ""

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Maven is not installed. Please install Maven first."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 17 or higher."
    exit 1
fi

# Build the project
echo "📦 Building the project..."
mvn clean install -DskipTests
echo "✅ Build completed successfully!"
echo ""

# Function to start a service
start_service() {
    local service_name=$1
    local port=$2
    
    echo "Starting $service_name on port $port..."
    cd $service_name
    mvn spring-boot:run &
    cd ..
    sleep 5
}

# Start infrastructure services
echo "🏗️  Starting infrastructure services..."
start_service "eureka-server" "8761"
echo "⏳ Waiting for Eureka Server to be ready..."
sleep 15

start_service "config-server" "8888"
echo "⏳ Waiting for Config Server to be ready..."
sleep 10

start_service "api-gateway" "8080"
echo "⏳ Waiting for API Gateway to be ready..."
sleep 10

# Start business services
echo "💼 Starting business services..."
start_service "crm-service" "8081"
start_service "pos-service" "8082"
start_service "timesheet-service" "8083"
start_service "project-management-service" "8084"
start_service "subscriptions-service" "8085"
start_service "sales-service" "8086"
start_service "employees-service" "8087"
start_service "helpdesk-service" "8088"
start_service "planning-service" "8089"
start_service "inventory-service" "8090"

echo ""
echo "✅ All services started successfully!"
echo ""
echo "📊 Service URLs:"
echo "   Eureka Dashboard: http://localhost:8761"
echo "   API Gateway:      http://localhost:8080"
echo "   Config Server:    http://localhost:8888"
echo ""
echo "📡 Business Services (via API Gateway):"
echo "   CRM:              http://localhost:8080/api/crm/customers"
echo "   POS:              http://localhost:8080/api/pos/sales"
echo "   Timesheet:        http://localhost:8080/api/timesheet/entries"
echo "   Projects:         http://localhost:8080/api/projects/projects"
echo "   Subscriptions:    http://localhost:8080/api/subscriptions/subscriptions"
echo "   Sales:            http://localhost:8080/api/sales/salesorders"
echo "   Employees:        http://localhost:8080/api/employees/employees"
echo "   Helpdesk:         http://localhost:8080/api/helpdesk/tickets"
echo "   Planning:         http://localhost:8080/api/planning/plans"
echo "   Inventory:        http://localhost:8080/api/inventory/products"
echo ""
echo "⚠️  Note: Make sure MongoDB is running on localhost:27017"
echo "   You can start MongoDB with: docker run -d -p 27017:27017 --name mongodb mongo:7.0"
echo ""
echo "Press Ctrl+C to stop all services"
wait
