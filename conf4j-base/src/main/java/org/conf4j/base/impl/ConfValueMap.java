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
package org.conf4j.base.impl;

import static org.conf4j.base.dsl.EScope.undefined;
import static org.conf4j.base.dsl.EScope.unit_test;

import java.io.IOException;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.conf4j.base.ConfElements;
import org.conf4j.base.dsl.EScope;
import org.conf4j.base.impl.ConfValue.ESource;

public final class ConfValueMap extends ConcurrentHashMap<String, ConfValue> {
    private static final long serialVersionUID = 1L;
    private MessageFormat USAGE_0_INVALID_FOR_ELT_1 = new MessageFormat(
                    "usage ''{0}'' is not a valid for config element ''{1}''");
    private MessageFormat USAGE_0_NOT_FOUND_FOR_ELT_1_SOURCE_2 = new MessageFormat(
                    "usage ''{0}'' not found for config element ''{1}'' and source ''{2}''");
    private MessageFormat USAGE_0_SEEMS_TO_BE_UNUSED_FOR_USAGE_1 = new MessageFormat(
                    "usage ''{0}'' seems to be unused for usage ''{1}''");
    static final List<EScope> UNDEFINED_USAGE = Arrays.asList(new EScope[] { EScope.undefined });

    public ConfValue put(String name, String value, ESource source) {
        final ConfValue configValue = get(name);
        final String description = configValue == null ? "" : configValue.getDescription();
        final List<EScope> usages = configValue == null ? UNDEFINED_USAGE : configValue.getUsages();
        final boolean devPurposeOnly = configValue == null ? false : configValue.isDevPurposeOnly();
        return put(name, new ConfValue(value, source, description, usages, devPurposeOnly));
    }

    public final void checkUsage(ConfValue value, String name, PrintStream os) throws IOException {
        final ConfValue appname = super.get(ConfElements.scope);
        final EScope usage = appname == null ? undefined : EScope.valueOf(appname.getValue(false));
        if (value == null)
            return;
        if (usage == null) {
            os.println(USAGE_0_INVALID_FOR_ELT_1.format(new Object[] { usage, name }));
            return;
        }
        if (value.getUsages().size() == 0)
            return;
        if (value.getUsages().size() == 1 && value.getUsages().contains(undefined))
            return;
        if (!value.getUsages().contains(usage) && value.getAccessCount() > 0) {
            os.println(USAGE_0_NOT_FOUND_FOR_ELT_1_SOURCE_2.format(new Object[] { usage, name, value.getSource() }));
            return;
        }
    }

    public final void checkUnused(PrintStream os) throws IOException {
        final ConfValue appname = super.get(ConfElements.scope);
        if (appname == null)
            return;
        final EScope usage = EScope.valueOf(appname.getValue(false));
        if (usage == null)
            return;
        if (usage == unit_test)
            return;

        for (Map.Entry<String, ConfValue> entry : entrySet()) {
            if (!entry.getValue().getUsages().contains(usage))
                continue;
            if (entry.getValue().getAccessCount() == 0)
                os.println(USAGE_0_SEEMS_TO_BE_UNUSED_FOR_USAGE_1.format(new Object[] { entry.getKey(), usage }));
        }
    }
}
