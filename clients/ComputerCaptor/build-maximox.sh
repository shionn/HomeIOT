#!/bin/sh

mvn clean package -P MaxiMox
echo "deploy"
scp target/ComputerCaptor-1.0.jar root@192.168.10.11:/root/
echo "fin"

