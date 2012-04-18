package org.conf4j.webapp.listener;

import static org.conf4j.ConfElements.config_dump;
import static org.conf4j.ConfElements.full_config_dump;
import static org.conf4j.ConfElements.scope;
import static org.conf4j.service.ConfServiceInstance.CONF;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.conf4j.EScope;

@WebListener("Conf4jContextListener")
public class Conf4jContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("**********************************************************************************************************************************");
        System.err.println("*");
        System.out.println("*  Conf4jContextListener#contextInitialized [starting    ...]");
        System.err.println("*");
        System.out.println("**********************************************************************************************************************************");
        try {
            CONF.setValue(scope, EScope.webapp.name());
            CONF.initFolders();
            if (CONF.getBooleanValue(full_config_dump))
                CONF.dumpConf(System.out, false);
            else if (CONF.getBooleanValue(config_dump))
                CONF.dumpConf(System.out, true);
        } catch (RuntimeException e) {
            System.err.println("**********************************************************************************************************************************");
            System.err.println("*");
            System.err.println("*  Conf4jContextListener#contextInitialized [failed]");
            System.err.println("*");
            System.err.print("*  ");
            System.err.println(e.getMessage());
            System.err.println("*");
            System.err.println("**********************************************************************************************************************************");
            throw e;
        }
        System.out.println("**********************************************************************************************************************************");
        System.err.println("*");
        System.out.println("*  Conf4jContextListener#contextInitialized [succeed]");
        System.err.println("*");
        System.out.println("**********************************************************************************************************************************");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
