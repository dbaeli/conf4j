package org.conf4j.service;

import static org.conf4j.ConfElements.devoxx_home_url;
import static org.conf4j.ConfElements.devoxx_unittest_url;
import static org.conf4j.service.ConfServiceInstance.CONF;

public class SampleServiceWithConf {
    public String getDevoxxHome() {
        return CONF.getValue(devoxx_home_url);
    }

    public String getDevoxxUnitTest() {
        return CONF.getValue(devoxx_unittest_url);
    }
}
