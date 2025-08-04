#include "dashboard.h"

Dashboard::Dashboard(Display* display, HomeIot* iot) {
	this->display = display;
	this->iot = iot;
}

void Dashboard::draw() {
	// this->display->drawCenterText(160, 30, "Temperature O11dw", RGB565_BLACK);
	// this->drawTempBar(50, "Cpu", this->cpu, 0, 90);
	// this->drawTempBar(72, "Gpu", this->gpu, 0, 90);
	// this->drawTempBar(94, "Nvme", this->nvme, 0, 90);
	this->display->drawCenterText(240, 30, "Temperature", RGB565_BLACK);
	this->drawTempBar(50, "Exterieur", this->exterior, 0, 50);
	this->drawTempBar(72, "Bureau", this->office, 0, 50);
	this->drawTempBar(94, "Morgan", this->bedroom, 0, 50);
	this->display->drawCenterText(240, 130, "Electricite", RGB565_BLACK);
	this->drawElecBar(150, "Consso.", this->elec_consso, 0, 6);
	this->drawElecBar(172, "Prod.", this->elec_prod, 0, 6);
}

void Dashboard::drawTempBar(uint16_t y, String text, float_t value, uint16_t min, uint16_t max) {
	uint16_t color = RGB565_ORANGE;
	if (value - min <= (max - min) / 2) {
		color = RGB565_DARKGREEN;
	}
	if (value - min >= 3 * (max - min) / 4) {
		color = RGB565_RED;
	}
	this->display->fillRoundRect(10, y, 460, 20, 3, RGB565_LIGHTGREY);
	this->display->fillRoundRect(10, y, map(value, min, max, 0, 460), 20, 3, color);
	this->display->drawText(13, y + 3, text, RGB565_BLACK);
	this->display->drawRightText(367, y + 3, String(value, 1) + "c", RGB565_BLACK);
	this->display->drawRoundRect(10, y, 460, 20, 3, RGB565_BLACK);
}

void Dashboard::drawElecBar(uint16_t y, String text, float_t value, float_t min, float_t max) {
	uint16_t color = RGB565_ORANGE;
	if (value - min <= (max - min) / 2) {
		color = RGB565_DARKGREEN;
	}
	if (value - min >= 3 * (max - min) / 4) {
		color = RGB565_RED;
	}
	this->display->fillRoundRect(10, y, 460, 20, 3, RGB565_LIGHTGREY);
	this->display->fillRoundRect(10, y, map(value * 1000, min * 1000, max * 1000, 0, 300), 20, 3, color);
	this->display->drawText(13, y + 3, text, RGB565_BLACK);
	this->display->drawRightText(367, y + 3, String(value, 3) + "kw", RGB565_BLACK);
	this->display->drawRoundRect(10, y, 460, 20, 3, RGB565_BLACK);
}


bool Dashboard::update() {
	if (!office || millis() > lastUpdated + 60 * 1000) {
		lastUpdated = millis();
		this->office = iot->getCaptorF(100);
		this->bedroom = iot->getCaptorF(101);
		this->elec_consso = iot->getHaF("sensor.envoy_122333059807_consommation_electrique_actuelle");
		this->elec_prod = iot->getHaF("sensor.envoy_122333059807_production_d_electricite_actuelle");
		this->exterior = iot->getHaF("weather.forecast_maison", "temperature");
		return true;
	}
	return false;
};

boolean Dashboard::pressed(uint16_t touchX, uint16_t touchY) { return false; }
boolean Dashboard::released(uint16_t touchX, uint16_t touchY) { return false; }
