#include <Arduino.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include <WebServer.h>
#include <uri/UriBraces.h>
#include <Adafruit_NeoPixel.h>

#include "config.h"

#define HOST_HOMEIOT "http://homeiot/captor/"

HTTPClient http;
WiFiClient client;
WebServer server(80);

Adafruit_NeoPixel strip = Adafruit_NeoPixel(LED_LEN, LED_PIN, NEO_GRB + NEO_KHZ800);

boolean state = false;
uint32_t color = 0xFFFFFFFF;

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
		digitalWrite(LED_BUILTIN, HIGH);
		delay(50);
		digitalWrite(LED_BUILTIN, LOW);
		delay(50);
	}
	Serial.println();
	Serial.print("Connected. Ip: ");
	Serial.println(WiFi.localIP().toString());

	Serial.print("Connected. Mac: ");
	Serial.println(WiFi.macAddress());
}

void subscrib(uint captor) {
	String url = HOST_HOMEIOT + String(captor) + String("/register");
	if (http.begin(client, url)) {
		http.POST("");
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
	if (captor == CAPTOR_STATE) {
		state = value.equalsIgnoreCase("on");
	} else if (captor == CAPTOR_HSV) {
		color = strtoul(value.c_str(), NULL, 16);
	}
	server.send(200, "text/plain", "OK");

}

void setup() {
	Serial.begin(9600);
	pinMode(LED_BUILTIN, OUTPUT);
	initWifi();

	server.on(UriBraces("/captor/{}"), HTTP_PUT, receiveCaptorValue);
	server.on(UriBraces("/captor/{}"), HTTP_POST, receiveCaptorValue);
	server.begin();

	subscrib(CAPTOR_STATE);
	subscrib(CAPTOR_HSV);
}

void loop() {
	server.handleClient();
	if (state) {
		strip.begin();
		strip.fill(strip.ColorHSV(color >> 16 & 0xFFFF, color >> 8 & 0xFF, color & 0xFF));
		strip.show();
	} else {
		strip.clear();
		strip.show();
	}
}
