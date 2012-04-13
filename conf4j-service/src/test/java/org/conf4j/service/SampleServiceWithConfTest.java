package org.conf4j.service;

import static org.conf4j.base.impl.ConfServiceImpl.CONF;

import org.junit.Test;

public class SampleServiceWithConfTest {
    @Test
    public void dumpConf() {
        CONF.dumpConf(System.out, true);
    }

    @Test
    public void askDevoxxHome() {
        new SampleServiceWithConf().askDevoxxHome();
    }

    @Test
    public void askDevoxxAgenda() {
        new SampleServiceWithConf().askDevoxxAgenda();
    }
}
