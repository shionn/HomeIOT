
#ifndef __DISPLAY_H__
#define __DISPLAY_H__

#include <Arduino.h>
#include <Arduino_GFX_Library.h>
#include <Wire.h>

#define DISPLAY_BACKLIGHT 1
#define TOUCH_ADDR 0x3B
#define TOUCH_SDA 4
#define TOUCH_SCL 8
#define TOUCH_I2C_CLOCK 400000
#define TOUCH_RST_PIN 12
#define TOUCH_INT_PIN 11
#define AXS_MAX_TOUCH_NUMBER 1


class Display {
private:
	Arduino_DataBus* bus = new Arduino_ESP32QSPI(45, 47, 21, 48, 40, 39);
	Arduino_GFX* g = new Arduino_AXS15231B(bus, GFX_NOT_DEFINED, 0, false, 320, 480);
	Arduino_Canvas* gfx = new Arduino_Canvas(320, 480, g, 0, 0, 0);
public:
	uint16_t touchX, touchY;

public:
	void init();
	Arduino_Canvas* getGfx();

	void drawCircle(uint16_t x, uint16_t y, uint16_t r, uint16_t color);
	void drawCenterText(uint16_t x, uint16_t y, const String& text, uint16_t color);
	void drawRightText(uint16_t x, uint16_t y, const String& text, uint16_t color);
	void drawRect(uint16_t x, uint16_t y, uint16_t w, uint16_t h, uint16_t color);
	void drawRoundRect(uint16_t x, uint16_t y, uint16_t w, uint16_t h, uint16_t r, uint16_t color);
	void drawSprite(uint16_t x, uint16_t y, const uint16_t* sprite);
	void drawText(uint16_t x, uint16_t y, const String& text, uint16_t color);
	void fillCircle(uint16_t x, uint16_t y, uint16_t r, uint16_t color);
	void fillRect(uint16_t x, uint16_t y, uint16_t w, uint16_t h, uint16_t color);
	void fillRoundRect(uint16_t x, uint16_t y, uint16_t w, uint16_t h, uint16_t r, uint16_t color);
	void fillScreen(u16_t color);
	void fillTriangle(uint16_t x1, uint16_t y1, uint16_t x2, uint16_t y2, uint16_t x3, uint16_t y3, uint16_t color);

	void flush();

	bool isTouched();
};

#endif