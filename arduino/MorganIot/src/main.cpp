#include <ESP8266WiFi.h>
#include <ESP8266HTTPClient.h>
#include <DHTesp.h>
#include <TM1637Display.h>
#include <TaskScheduler.h>

#ifdef ESP32
#pragma message(THIS EXAMPLE IS FOR ESP8266 ONLY!)
#error Select ESP8266 board.
#endif

#define LETTER_C SEG_A | SEG_D | SEG_E | SEG_F
#define LETTER_N SEG_A | SEG_B | SEG_C | SEG_E | SEG_F
#define LETTER_O SEG_A | SEG_B | SEG_C | SEG_D | SEG_E | SEG_F

#define SSID_NAME "AsusHome"
#define SSID_PASS "aazzeerrttyy"

// #define SSID_NAME "Livebox-AF32"
// #define SSID_PASS "EFE356954211D29CFDE6ECAD64"


#define BUTTON D5

DHTesp dht;
TM1637Display display(  D2, // CLOCK Blanc
                        D3); // DATA Jaune
WiFiClient client;
HTTPClient http;

float temperature = -1;

void readTemperature() {
  if (http.begin(client, "http://192.168.50.69:8080/HomeIOT/captor/101/")) {
    temperature = dht.getTemperature();
    int status = http.PUT(String(temperature));
    http.end();
  } 
}

void reconnectIfNeed() {
  if (WiFi.status() != WL_CONNECTED) {
    uint8_t text[] = { LETTER_C, LETTER_O, LETTER_N, LETTER_N };
    display.setSegments(text);
    delay(500);
    display.clear();
  }
}

Scheduler runner;
Task readTemperatureTask(10 * TASK_MINUTE, TASK_FOREVER, &readTemperature, &runner, true);
Task reconnectIfNeedTask(10 * TASK_SECOND, TASK_FOREVER, &reconnectIfNeed, &runner, true);

void initNetwork() {
  WiFi.disconnect();
  WiFi.setHostname( "MorganIOT" );
  WiFi.mode(WIFI_STA);
  WiFi.setAutoConnect(true);
  WiFi.setAutoReconnect(true);
  WiFi.begin( SSID_NAME, SSID_PASS );
}

void setup() {
  // Serial.begin(115200);

  pinMode(BUTTON, INPUT_PULLUP);

  dht.setup(D1, DHTesp::DHT22);
  
  display.setBrightness(0x0f);
  display.clear();
  
  initNetwork();
  
  runner.startNow();
  readTemperatureTask.delay();
}

void loop() {
  runner.execute();
  if (digitalRead(BUTTON) == LOW) {
    uint8_t data[] = { LETTER_C };
    display.showNumberDecEx(temperature*10, 0b11100000, false, 3, 0);
    display.setSegments(data, 1, 3);
  } else {
    display.clear();
  }
}
