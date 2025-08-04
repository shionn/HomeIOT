
#include "homeiot.h"

HomeIot::HomeIot() {

}

void HomeIot::init() {

};

float_t HomeIot::getCaptorF(uint16_t captor) {
	float_t r = 0;
	if (this->http.begin(this->client, HOST_CAPTOR + String(captor))) {
		if (this->http.GET() == 202) {
			r = this->http.getString().toFloat();
		}
		this->http.end();
	}
	return r;
};

float_t HomeIot::getHaF(String captor) {
	float_t r = 0;
	String url = HOST_HA + captor;
	if (this->http.begin(this->client, url)) {
		int status = this->http.GET();
		if (status == 202) {
			r = this->http.getString().toFloat();
		}
		this->http.end();
	}
	return r;
};

float_t HomeIot::getHaF(String captor, String attr) {
	float_t r = 0;
	String url = HOST_HA + captor + "/" + attr;
	if (this->http.begin(this->client, url)) {
		int status = this->http.GET();
		if (status == 202) {
			r = this->http.getString().toFloat();
		}
		this->http.end();
	}
	return r;
};
