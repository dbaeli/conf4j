package org.conf4j.service;

import static org.conf4j.base.ConfElements.devoxx_agenda_url;
import static org.conf4j.base.ConfElements.devoxx_home_url;
import static org.conf4j.base.impl.ConfServiceImpl.CONF;

public class SampleServiceWithConf {
    public void askDevoxxHome() {
        System.out.println("Devoxx home url: " + CONF.getValue(devoxx_home_url));
    }

    public void askDevoxxAgenda() {
        System.out.println("Devoxx agenda url: " + CONF.getValue(devoxx_agenda_url));
    }
}
