package org.conf4j.sample;

import static org.conf4j.service.ConfServiceInstance.CONF;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.conf4j.sample.SampleWithConf;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * in your IDE you need to define a JVM variable -Ddevoxx_base_url=test.devoxx.fr
 * 
 * in maven it's define as system property for the surefire plugin
 */
public class SampleWithConfTest {
    private final SampleWithConf svc = new SampleWithConf();

    @BeforeClass
    public static void dumpConf() {
        CONF.dumpConf(System.out, true);
    }

    @Test
    public void testDevoxxUnitTest_test_devoxx_fr() {
        assertEquals("http://test.devoxx.fr/display/FR12/UnitTest", svc.getDevoxxUnitTest());
    }
}
