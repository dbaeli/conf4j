package org.conf4j.service;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * in your IDE you need to define a JVM variable -Ddevoxx_base_url=test.devoxx.fr
 * 
 * in maven it's define as system property for the surefire plugin
 */
public class SampleServiceWithConfTest {
    private final SampleServiceWithConf svc = new SampleServiceWithConf();

    @Test
    public void testDevoxxHome_localhost() {
        assertNotSame("http://localhost/display/FR12/Accueil", svc.getDevoxxHome());
    }

    @Test
    public void testDevoxxAgenda_localhost() {
        assertNotSame("http://localhost/display/FR12/Agenda", svc.getDevoxxAgenda());
    }

    @Test
    public void testDevoxxHome_test_devoxx_fr() {
        assertEquals("http://test.devoxx.fr/display/FR12/Accueil", svc.getDevoxxHome());
    }

    @Test
    public void testDevoxxAgenda_test_devoxx_fr() {
        assertEquals("http://test.devoxx.fr/display/FR12/Agenda", svc.getDevoxxAgenda());
    }
}
