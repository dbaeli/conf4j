/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.conf4j.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang3.SystemUtils;

class Environment {
    public static String OS_NAME = System.getProperty("os.name").toLowerCase(Locale.US);

    static private Properties env;

    static public boolean isOSWindows() {
        return OS_NAME.indexOf("windows") >= 0;
    }

    static public Properties getProperties() {
        return getEnvironment();
    }

    static public String getProperty(String name) {
        return getEnvironment().getProperty(name);
    }

    static public String getProperty(String name, String defaultValue) {
        return getEnvironment().getProperty(name, defaultValue);
    }

    static private synchronized Properties getEnvironment() {
        if (env != null) {
            return env;
        }

        env = new Properties();

        try {
            final String osname = System.getProperty("os.name", "<unknown>").toLowerCase();
            String[] cmd = new String[] { "/bin/sh", "-c", "env" };
            String enc = null;

            if (osname.indexOf("windows") >= 0) {
                // should detect win9x versions (they use command.com)
                cmd = new String[] { "cmd", "/c", "set" };
            } else if (osname.indexOf("linux") >= 0) {
            } else if (osname.indexOf("sunos") >= 0) {
            } else if (osname.indexOf("mac os x") >= 0) {
            } else if (osname.indexOf("z/os") >= 0) {
                enc = "Cp1047";
            } else {
                System.out.println("[WARNING] Unrecognized OS '" + osname + "', trying UNIX-like.");
            }

            Process ps = Runtime.getRuntime().exec(cmd);
            InputStream is = ps.getInputStream();
            BufferedReader reader = null;

            if (enc != null) {
                try {
                    reader = new BufferedReader(new InputStreamReader(is, enc));
                } catch (UnsupportedEncodingException e) {
                    System.out.println("[WARNING] Unsupported encoding " + e.getMessage()
                                    + ", using platform default instead.");
                }
            }
            if (reader == null) {
                reader = new BufferedReader(new InputStreamReader(is));
            }
            final String sep = SystemUtils.LINE_SEPARATOR;
            String var = null;
            String value = null;
            String line;
            while ((line = reader.readLine()) != null) {
                int pos = line.indexOf('=');

                // Beware of multi-line values
                if (pos >= 0) {
                    if (var != null) {
                        env.setProperty(var, value);
                    }

                    var = line.substring(0, pos);
                    value = line.substring(pos + 1);
                } else {
                    if (value == null) {
                        // should not happen
                        value = line;
                    } else {
                        value += (sep + line);
                    }
                }
            }

            if (var != null) {
                env.setProperty(var, value);
            }
        } catch (Throwable t) {
            System.out.println("[Error] while reading environment. " + t.getMessage());
            t.printStackTrace();
        }

        return env;
    }
}
