
#ifndef __HOME_IOT_H__
#define __HOME_IOT_H__

#include <Arduino.h>
#include <HTTPClient.h>

#define HOST_CAPTOR "http://homeiot/captor/"
#define HOST_HA "http://homeiot/ha/"

class HomeIot {
private:
	WiFiClient client;
	HTTPClient http;
public:
	HomeIot();
	void init();
	float_t getCaptorF(uint16_t captor);
	float_t getHaF(String captor);
	float_t getHaF(String captor, String attr);

};

#endif