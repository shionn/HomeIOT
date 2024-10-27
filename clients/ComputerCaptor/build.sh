#!/bin/sh

mvn clean package
echo "deploy"
#sudo mkdir /usr/share/homeiot/
sudo cp target/*.jar /usr/share/homeiot/
sudo cp src/main/systemd/HomeIotHardwareTemp.service /etc/systemd/system/
sudo chmod 644 /etc/systemd/system/HomeIotHardwareTemp.service
sudo systemctl daemon-reload
sudo systemctl enable HomeIotHardwareTemp.service
echo "fin"
