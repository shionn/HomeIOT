
#include <Arduino.h>
#include <WiFi.h>

#ifndef __NETWORK_H__
#define __NETWORK_H__

#include "ssid.h"

class Network {
public:
	void init();
	bool connected();

};


#endif

