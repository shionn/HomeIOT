#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>

#define SSID_NAME "AsusHome"
#define SSID_PASS "aazzeerrttyy"
#define HOST_NAME "LightObjectDev"

//#define HOST_HOMEIOT "http://homeiot/captor/"
#define HOST_HOMEIOT "http://192.168.10.32:8080/HomeIOT/captor/"

HTTPClient http;
WiFiClient client;

void initWifi() {
	WiFi.disconnect(true);
	// il faut faire le setHostName avant le mode sinon ce n'est pas pris en compte. 
	WiFi.setHostname(HOST_NAME);
	// mode client
	WiFi.mode(WIFI_STA);
	WiFi.setAutoConnect(true);
	WiFi.setAutoReconnect(true);
	WiFi.begin(SSID_NAME, SSID_PASS);

	while (!WiFi.isConnected()) {
		Serial.print(".");
		delay(100);
	}
	Serial.println();
	Serial.print("Connected. Ip: ");
	Serial.println(WiFi.localIP().toString());

	Serial.print("Connected. Mac: ");
	Serial.println(WiFi.macAddress());
}

void setup() {
	Serial.begin(9600);
	initWifi();
	pinMode(LED_BUILTIN, OUTPUT);
	digitalWrite(LED_BUILTIN, HIGH);

	String url = HOST_HOMEIOT + String(200) + String("/register");
	if (http.begin(client, url)) {
		int status = http.POST("");
		Serial.println(status);
		http.end();
	}

}

void loop() {
  // put your main code here, to run repeatedly:
}
