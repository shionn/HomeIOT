
#include "topbar.h"

TopBar::TopBar(Display* display, Horloge* horloge, Network* network) {
	this->display = display;
	this->horloge = horloge;
	this->network = network;
};

void TopBar::draw() {
	display->fillRect(0, 0, 480, 18, RGB565_BLACK);
	String date = horloge->getHour() < 10 ? "0" + String(horloge->getHour()) : String(horloge->getHour());
	date += (horloge->getSecond()) % 2 ? ":" : " ";
	date += horloge->getMinute() < 10 ? "0" + String(horloge->getMinute()) : String(horloge->getMinute());
	display->drawCenterText(240, 2, date, RGB565_WHITE);
	if (network->connected()) {
		display->drawSprite(466, 3, wifi);
	}
};



