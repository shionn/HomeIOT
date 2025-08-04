
#include "horloge.h"
WiFiUDP udp;
NTPClient ntpClient(udp, NTP_SERVER, 0, REFRESH_INTERVAL_IN_MS);
TimeChangeRule cest = { "CEST", Last, Sun, Mar, 2, 120 };
TimeChangeRule cet = { "CET", Last, Sun, Oct, 3, 60 };
Timezone timezone(cest, cet);

void Horloge::init() {
	ntpClient.begin();
};

int Horloge::getHour() {
	ntpClient.update();
	time_t utcTime = ntpClient.getEpochTime();
	time_t localTime = timezone.toLocal(utcTime);
	return hour(localTime);
};

int Horloge::getMinute() {
	time_t utcTime = ntpClient.getEpochTime();
	time_t localTime = timezone.toLocal(utcTime);
	return minute(localTime);
};

int Horloge::getSecond() {
	time_t utcTime = ntpClient.getEpochTime();
	time_t localTime = timezone.toLocal(utcTime);
	return second(localTime);
};
