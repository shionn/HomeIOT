
#include "display.h"

void Display::init() {
	this->gfx->begin();
	this->gfx->setRotation(1);
	this->gfx->setTextSize(2);
	pinMode(DISPLAY_BACKLIGHT, OUTPUT);
	digitalWrite(DISPLAY_BACKLIGHT, HIGH);

	// Initialize touch
	Wire.begin(TOUCH_SDA, TOUCH_SCL);
	Wire.setClock(TOUCH_I2C_CLOCK);

	// Configure touch pins
	pinMode(TOUCH_INT_PIN, INPUT_PULLUP);
	pinMode(TOUCH_RST_PIN, OUTPUT);
	digitalWrite(TOUCH_RST_PIN, LOW);
	delay(200);
	digitalWrite(TOUCH_RST_PIN, HIGH);
	delay(200);
};

Arduino_Canvas* Display::getGfx() {
	return this->gfx;
};

void Display::drawSprite(uint16_t x, uint16_t y, const uint16_t* sprite) {
	this->gfx->draw16bitRGBBitmapWithTranColor(x, y, const_cast<uint16_t*>(sprite) + 2, RGB565(255, 0, 255), sprite[0], sprite[1]);
};

void Display::drawText(uint16_t x, uint16_t y, const String& text, uint16_t color) {
	this->gfx->setCursor(x, y);
	this->gfx->setTextColor(color);
	this->gfx->print(text);
};

void Display::drawCenterText(uint16_t x, uint16_t y, const String& text, uint16_t color) {
	this->gfx->setCursor(x - text.length() * 6, y);
	this->gfx->setTextColor(color);
	this->gfx->print(text);
};

void Display::drawRightText(uint16_t x, uint16_t y, const String& text, uint16_t color) {
	this->gfx->setCursor(x - text.length() * 12, y);
	this->gfx->setTextColor(color);
	this->gfx->print(text);
};

void Display::fillScreen(uint16_t color) {
	this->gfx->fillScreen(color);
};

void Display::drawRect(uint16_t x, uint16_t y, uint16_t w, uint16_t h, uint16_t color) {
	this->gfx->drawRect(x, y, w, h, color);
};
void Display::drawRoundRect(uint16_t x, uint16_t y, uint16_t w, uint16_t h, uint16_t r, uint16_t color) {
	this->gfx->drawRoundRect(x, y, w, h, r, color);
};

void Display::fillRect(uint16_t x, uint16_t y, uint16_t w, uint16_t h, uint16_t color) {
	this->gfx->fillRect(x, y, w, h, color);
};

void Display::fillRoundRect(uint16_t x, uint16_t y, uint16_t w, uint16_t h, uint16_t r, uint16_t color) {
	this->gfx->fillRoundRect(x, y, w, h, r, color);
};

void Display::drawCircle(uint16_t x, uint16_t y, uint16_t r, uint16_t color) {
	this->gfx->drawCircle(x, y, r, color);
};

void Display::fillCircle(uint16_t x, uint16_t y, uint16_t r, uint16_t color) {
	this->gfx->fillCircle(x, y, r, color);
};

void Display::fillTriangle(uint16_t x1, uint16_t y1, uint16_t x2, uint16_t y2, uint16_t x3, uint16_t y3, uint16_t color) {
	this->gfx->fillTriangle(x1, y1, x2, y2, x3, y3, color);
};


void Display::flush() {
	this->gfx->flush();
};

bool Display::isTouched() {
	uint8_t data[AXS_MAX_TOUCH_NUMBER * 6 + 2] = { 0 };

	// Define the read command array properly
	const uint8_t read_cmd[11] = {
		0xb5, 0xab, 0xa5, 0x5a, 0x00, 0x00,
		(uint8_t)((AXS_MAX_TOUCH_NUMBER * 6 + 2) >> 8),
		(uint8_t)((AXS_MAX_TOUCH_NUMBER * 6 + 2) & 0xff),
		0x00, 0x00, 0x00
	};

	Wire.beginTransmission(TOUCH_ADDR);
	Wire.write(read_cmd, 11);
	while (Wire.endTransmission() != 0);

	while (Wire.requestFrom(TOUCH_ADDR, sizeof(data)) != sizeof(data));

	for (int i = 0; i < sizeof(data); i++) {
		data[i] = Wire.read();
	}

	if (data[1] > 0 && data[1] <= AXS_MAX_TOUCH_NUMBER) {
		uint16_t rawX = ((data[2] & 0x0F) << 8) | data[3];
		uint16_t rawY = ((data[4] & 0x0F) << 8) | data[5];
		if (rawX > 500 || rawY > 500) return false;
		this->touchX = map(rawX, 0, 320, 320, 0);
		this->touchY = map(rawY, 0, 480, 480, 0);
		return true;
	}
	return false;
};

