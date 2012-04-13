package org.conf4j.service;

import static org.conf4j.base.ConfElements.tell_me_why;
import static org.conf4j.base.impl.ConfServiceImpl.CONF;

public class SampleServiceWithConf {
    public void tellMeWhy() {
        System.out.println(CONF.getValue(tell_me_why));
    }
}
