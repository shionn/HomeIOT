
#ifndef __HORLOGE_H__
#define __HORLOGE_H__

#include <WiFi.h>
#include <WiFiUdp.h>
#include <NTPClient.h>
#include <Timezone.h>

#define NTP_SERVER "pool.ntp.org"
#define REFRESH_INTERVAL_IN_MS 600000


class Horloge {
public:
	void init();

	int getHour();
	int getMinute();
	int getSecond();


};

#endif
