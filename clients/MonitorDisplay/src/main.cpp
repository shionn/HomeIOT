#include <Arduino.h>

#include "dashboard.h"
#include "display.h"
#include "horloge.h"
#include "network.h"
#include "topbar.h"

Network network;
Horloge horloge;
Display display;
HomeIot homeiot;

TopBar topbar(&display, &horloge, &network);
Dashboard dashboard(&display, &homeiot);

void setup() {
	Serial.begin(115200);

	display.init();
	display.fillScreen(RGB565_DARKGREY);
	display.drawCenterText(240, 160, F("Starting..."), RGB565_LIGHTGREY);
	display.flush();


	network.init();
	horloge.init();
}

void loop() {
	bool changed = dashboard.update();
	if (changed) {
		display.fillScreen(RGB565_DARKGREY);
	}

	topbar.draw();
	if (changed) {
		dashboard.draw();
	}
	display.flush();
}
