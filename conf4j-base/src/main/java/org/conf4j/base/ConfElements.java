/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.conf4j.base;

import java.lang.reflect.Field;

import org.conf4j.base.dsl.Conf4j;
import org.conf4j.base.dsl.EConfigUsage;

public class ConfElements {

    private ConfElements() {
    }

    @Conf4j(value = "${catalina.base}/conf/courtanet_config.properties", //
    description = "Fichier properties de configuration pour surcharger les valeurs de toutes les variables")
    public static String configuration_file;

    @Conf4j(value = "${catalina.base}/conf/courtanet_instance_config.properties", //
    description = "Fichier properties de configuration pour surcharger les valeurs de toutes les variables")
    public static String instance_configuration_file;

    /**
     * @see EConfigUsage
     */
    @Conf4j(value = "unit_test",//
    description = "Nom de l'application utilisé pour valider les usages de ConfigElement, @see EConfigUsage")
    public static String appname;

    @Conf4j(value = "false", //
    description = "Affiche au démarrage de tomcat la configuration des variables déclarées dans ConfElements sur System.out")
    public static String config_dump;

    @Conf4j(value = "false", //
    description = "Affiche au démarrage de tomcat la configuration des variables (systeme/jvm/courtanet sur System.out")
    public static String full_config_dump;

    static {
        if (configuration_file == null) {
            try {
                for (Field field : ConfElements.class.getDeclaredFields()) {
                    if (field.getAnnotation(Conf4j.class) == null)
                        continue;
                    field.set(null, field.getName());
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static final boolean isConfigElement(String key) {
        for (Field field : ConfElements.class.getDeclaredFields()) {
            if (field.getAnnotation(Conf4j.class) == null) {
                continue;
            }
            if (field.getName().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Conf4j(value = "http://localhost",//
    description = "the base url for devoxx website")
    public static String devoxx_base_url;

    @Conf4j(value = "${devoxx_base_url}/display/FR12/Accueil",//
    description = "the Accueil url for devoxx website")
    public static String devoxx_home_url;

    @Conf4j(value = "${devoxx_base_url}/display/FR12/Agenda",//
    description = "the base url for devoxx website")
    public static String devoxx_agenda_url;
}
