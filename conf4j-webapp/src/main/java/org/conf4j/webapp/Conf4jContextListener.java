package org.conf4j.webapp;

import static org.conf4j.base.ConfElements.appname;
import static org.conf4j.base.ConfElements.config_dump;
import static org.conf4j.base.ConfElements.full_config_dump;
import static org.conf4j.base.impl.ConfServiceImpl.CONF;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.conf4j.base.dsl.EConfigUsage;

@WebListener("Conf4jInitListener")
public class Conf4jContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Conf4jInitListener#contextInitialized [started]");
        CONF.setValue(appname, EConfigUsage.webapp.name());
        CONF.initFolders();
        if (CONF.getBooleanValue(full_config_dump))
            CONF.dumpConf(System.out, false);
        else if (CONF.getBooleanValue(config_dump))
            CONF.dumpConf(System.out, true);
        System.out.println("Conf4jInitListener#contextInitialized [done]");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
