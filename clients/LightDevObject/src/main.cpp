#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ESP8266WebServer.h>
#include <uri/UriBraces.h>

#define SSID_NAME "AsusHome"
#define SSID_PASS "aazzeerrttyy"
#define HOST_NAME "LightObjectDev"

#define CAPTOR 110

//#define HOST_HOMEIOT "http://homeiot/captor/"
#define HOST_HOMEIOT "http://192.168.10.32:8080/HomeIOT/captor/"

HTTPClient http;
WiFiClient client;
ESP8266WebServer server(80);

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

void subscrib() {
	String url = HOST_HOMEIOT + String(CAPTOR) + String("/register");
	if (http.begin(client, url)) {
		int status = http.POST("");
		Serial.println(http.getString());
		http.end();
	}
}

void receiveCaptorValue() {
	int captor = server.pathArg(0).toInt();
	String value = server.arg("plain");
	Serial.print(captor);
	Serial.print(" Receive ");
	Serial.println(value);
	server.send(200, "text/plain", "OK");
}

void setup() {
	Serial.begin(9600);
	initWifi();
	pinMode(LED_BUILTIN, OUTPUT);
	digitalWrite(LED_BUILTIN, HIGH);

	server.on(UriBraces("/captor/{}"), HTTP_PUT, receiveCaptorValue);
	server.on(UriBraces("/captor/{}"), HTTP_POST, receiveCaptorValue);
	server.begin();

	subscrib();
}

void loop() {
	server.handleClient();
}
