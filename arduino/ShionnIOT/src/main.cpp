#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ssd1309.h>
#include <DHTesp.h>
#include <TaskScheduler.h>


#define SSID_NAME "AsusHome"
#define SSID_PASS "aazzeerrttyy"
#define HOST "http://homeiot/captor/100"

#define AVG_TEMP_READ 5

#define SSD_CS D2 // CS ou SS ou CHIP SELECT : RS
#define SSD_RW D3 // DS
#define SSD_RS D4 // Reset, RS
// D7 :: DATA ou MOSI ou SDA ou R/W
// D6 :: MISO utilisé
// D5 ::  CLOCK, E, SCL

Ssd1309 lcd = Ssd1309(SSD_CS, SSD_RW, SSD_RS);
DHTesp dht;
WiFiClient client;
HTTPClient http;

float temperatureBureau = -1;
int status = 202;

void readTemperature() {
	if (http.begin(client, HOST)) {
		float temp = 0;
		for (int i = 0; i < AVG_TEMP_READ; i++) {
			temp += dht.getTemperature();
			delay(10);
		}
		temperatureBureau = temp / AVG_TEMP_READ;
		// status = http.PUT(String(temperatureBureau));
		http.end();
	}
}

Scheduler runner;
Task readTemperatureTask(/*10 * TASK_MINUTE */ TASK_SECOND * 5, TASK_FOREVER, &readTemperature, &runner, true);

void initNetwork() {
	WiFi.disconnect();
	WiFi.setHostname("ShionnIOT");

	WiFi.mode(WIFI_STA);
	WiFi.setAutoConnect(true);
	WiFi.setAutoReconnect(true);
	WiFi.begin(SSID_NAME, SSID_PASS);
}

void setup() {
	dht.setup(D1, DHTesp::DHT22);
	lcd.init();
	initNetwork();
	runner.startNow();
	readTemperatureTask.delay();
}

void loop() {
	runner.execute();
	lcd.clearBuffer();
	if (WiFi.status() != WL_CONNECTED) {
		lcd.print(0, 0, F("Not Copnnected"));
		lcd.print(0, 8, String(WiFi.status()));
	} else if (status != 202) {
		lcd.print(0, 0, F("Error on PUT"));
		lcd.print(0, 8, F(HOST));
		lcd.print(0, 16, String(status) + " :: " + http.errorToString(status));
	} else {
		lcd.print(0, 0, "Bureau  " + String(temperatureBureau, 1) + "c");
		lcd.print(0, 8, "Ch Morg " + String(23.2, 1) + "c");
		lcd.print(0, 16, "CPU " + String(36.7, 1) + "c");
		lcd.print(0, 24, "GPU " + String(42.5, 1) + "c");
	}
	lcd.display();
}
