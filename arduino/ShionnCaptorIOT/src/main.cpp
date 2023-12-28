#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <DHTesp.h>
#include <TaskScheduler.h>

#define SSID_NAME "AsusHome"
#define SSID_PASS "aazzeerrttyy"
#define HOST_CAPTOR "http://homeiot/captor/"

#define AVG_TEMP_READ 5

DHTesp dht;
WiFiClient client;
HTTPClient http;


int captor = 200;

void readLocalTemperature() {
	if (http.begin(client, HOST_CAPTOR + String(captor))) {
		float temp = 0;
		for (int i = 0; i < AVG_TEMP_READ; i++) {
			temp += dht.getTemperature();
			delay(10);
		}
		temp = temp / AVG_TEMP_READ;
		http.PUT(String(temp));
		http.end();
	}
}

Scheduler runner;
Task readLocalTemperatureTask(10 * TASK_MINUTE, TASK_FOREVER, &readLocalTemperature, &runner, true);

void initNetwork() {
	WiFi.disconnect(true);
	WiFi.mode(WIFI_STA);
	Serial.println(WiFi.getHostname());
	WiFi.setHostname("ShionnCaptorIOT");
	WiFi.setAutoConnect(true);
	WiFi.setAutoReconnect(true);
	WiFi.begin(SSID_NAME, SSID_PASS);
}

void setup() {
	Serial.begin(9600);
	dht.setup(D1, DHTesp::DHT22);
	initNetwork();
	runner.startNow();
	readLocalTemperatureTask.delay();
}

void loop() {
	runner.execute();
}
