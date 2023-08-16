#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <ssd1309.h>
#include <DHTesp.h>
#include <TaskScheduler.h>

#define SSID_NAME "AsusHome"
#define SSID_PASS "aazzeerrttyy"
#define HOST_CAPTOR "http://homeiot/captor/"

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


float temps[] = { -1, -1, -1, -1 };
int captor[] = { 100, 101, 111, 110 };
int status = 202;

void readLocalTemperature() {
	if (http.begin(client, HOST_CAPTOR + String(captor[0]))) {
		float temp = 0;
		for (int i = 0; i < AVG_TEMP_READ; i++) {
			temp += dht.getTemperature();
			delay(10);
		}
		temps[0] = temp / AVG_TEMP_READ;
		// status = http.PUT(String(temperatureBureau));
		http.end();
	}
}

void readServerTemperature() {
	for (uint8_t i = 1;i < 4; i++) {
		if (http.begin(client, HOST_CAPTOR + String(captor[i]))) {
			if (http.GET() == 202) {
				temps[i] = http.getString().toFloat();
			}
		}
		http.end();
	}
}

Scheduler runner;
Task readLocalTemperatureTask(/*10 * TASK_MINUTE */ TASK_SECOND * 5, TASK_FOREVER, &readLocalTemperature, &runner, true);
Task readServerTemperatureTask(/*10 * TASK_MINUTE */ TASK_SECOND * 5, TASK_FOREVER, &readServerTemperature, &runner, true);

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
	readLocalTemperatureTask.delay();
}

void loop() {
	runner.execute();
	lcd.clearBuffer();
	if (WiFi.status() != WL_CONNECTED) {
		lcd.print(0, 0, F("Not Copnnected"));
		lcd.print(0, 8, String(WiFi.status()));
	} else if (status != 202) {
		lcd.print(0, 0, F("Error on PUT"));
		lcd.print(0, 8, F(HOST_CAPTOR));
		lcd.print(0, 16, String(status) + " :: " + http.errorToString(status));
	} else {
		lcd.print(0, 0, "Bureau  " + String(temps[0], 1) + "c");
		lcd.print(0, 8, "Ch Morg " + String(temps[1], 1) + "c");
		lcd.print(0, 16, "CPU " + String(temps[2], 1) + "c");
		lcd.print(0, 24, "GPU " + String(temps[3], 1) + "c");
	}
	lcd.display();

}
