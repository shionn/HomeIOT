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
uint32_t color2 = 0xFFFFFFFF;
String mode = "static";

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
	} else if (captor == CAPTOR_HSV_2) {
		color2 = strtoul(value.c_str(), NULL, 16);
	} else if (captor == CAPTOR_MODE) {
		mode = value;
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
	subscrib(CAPTOR_HSV_2);
	subscrib(CAPTOR_MODE);
}

uint16_t step = 0;

void loop() {
	server.handleClient();
	if (state) {
		strip.begin();
		if (mode.equalsIgnoreCase(F("rainbow"))) {
			uint32_t c = strip.ColorHSV(map(step % RAINBOW_SPEED, 0, RAINBOW_SPEED, 0, 65535), 0xFF, color & 0xFF);
			strip.fill(c);
		} else if (mode.equalsIgnoreCase(F("theatre"))) {
			for (uint8_t led = 0; led < strip.numPixels(); led++) {
				uint16_t c = (map(led, 0, strip.numPixels(), 0, THEATRE_SPEED) + step) % THEATRE_SPEED;
				strip.setPixelColor(led, strip.ColorHSV(map(c, 0, THEATRE_SPEED, 0, 65535), 0xFF, color & 0xFF));
			}
		} else if (mode.equalsIgnoreCase(F("breath"))) {
			uint16_t hue = 0;
			uint8_t sat = 0;
			uint8_t val = 0;
			uint16_t v = step % (BREATH_SPEED * 2);
			if (v < BREATH_SPEED) {
				hue = map(v, 0, BREATH_SPEED, color >> 16 & 0xFFFF, color2 >> 16 & 0xFFFF);
				sat = map(v, 0, BREATH_SPEED, color >> 8 & 0xFF, color2 >> 8 & 0xFF);
				val = map(v, 0, BREATH_SPEED, color & 0xFF, color2 & 0xFF);
			} else {
				hue = map(v, BREATH_SPEED, BREATH_SPEED * 2, color2 >> 16 & 0xFFFF, color >> 16 & 0xFFFF);
				sat = map(v, BREATH_SPEED, BREATH_SPEED * 2, color2 >> 8 & 0xFF, color >> 8 & 0xFF);
				val = map(v, BREATH_SPEED, BREATH_SPEED * 2, color2 & 0xFF, color & 0xFF);
			}
			strip.fill(strip.ColorHSV(hue & 0xFFFF, sat & 0xFF, val & 0xFF));
		} else if (mode.equalsIgnoreCase(F("crawl"))) {
			for (uint8_t led = 0; led < strip.numPixels(); led++) {
				uint16_t v = (step / CRAWL_SPEED + strip.numPixels() - led) % CRAWL_LEN;
				uint16_t hue = map(v, 0, CRAWL_LEN, color >> 16 & 0xFFFF, color2 >> 16 & 0xFFFF);
				uint8_t	sat = map(v, 0, CRAWL_LEN, color >> 8 & 0xFF, color2 >> 8 & 0xFF);
				uint8_t val = map(v, 0, CRAWL_LEN, color & 0xFF, color2 & 0xFF);
				strip.setPixelColor(led, strip.ColorHSV(hue, sat, val));
			}
		} else {
			strip.fill(strip.ColorHSV(color >> 16 & 0xFFFF, color >> 8 & 0xFF, color & 0xFF));
		}
		strip.show();
	} else {
		strip.clear();
		strip.show();
	}
	step++;
	if (step > MAX_LOOP_EFFET) step -= MAX_LOOP_EFFET;
}
