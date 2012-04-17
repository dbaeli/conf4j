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
    private static MessageFormat SCOPE_0_NOT_DECLARED_FOR_ELT_1 = new MessageFormat(
                    "scope ''{0}'' not declared for ConfElement#{1}");
    private static MessageFormat SCOPE_0_NOT_DECLARED_FOR_ELT_1_SOURCE_2 = new MessageFormat(
                    "scope ''{0}'' not declared for ConfElement#{1} and source ''{2}''");
    private static MessageFormat SCOPE_0_SEEMS_TO_BE_UNUSED_FOR_USAGE_1 = new MessageFormat(
                    "scope ''{0}'' seems to be unused for scope ''{1}''");
    private static final List<EScope> SCOPE_UNDEFINED = Arrays.asList(new EScope[] { EScope.undefined });

    public ConfValue put(String name, String value, ESource source) {
        final ConfValue configValue = get(name);
        final String description = configValue == null ? "" : configValue.getDescription();
        final List<EScope> usages = configValue == null ? SCOPE_UNDEFINED : configValue.getScopes();
        final boolean devPurposeOnly = configValue == null ? false : configValue.isDevPurposeOnly();
        return put(name, new ConfValue(value, source, description, usages, devPurposeOnly));
    }

    public final void checkScope(ConfValue value, String name, PrintStream os) throws IOException {
        final ConfValue scopeValue = super.get(ConfElements.scope);
        final EScope scope = scopeValue == null ? undefined : EScope.valueOf(scopeValue.getValue(false));
        if (value == null)
            return;
        if (scope == null) {
            os.println(SCOPE_0_NOT_DECLARED_FOR_ELT_1.format(new Object[] { scope, name }));
            return;
        }
        if (value.getScopes().size() == 0)
            return;
        if (value.getScopes().size() == 1 && value.getScopes().contains(undefined))
            return;
        if (!value.getScopes().contains(scope) && value.getAccessCount() > 0) {
            os.println(SCOPE_0_NOT_DECLARED_FOR_ELT_1_SOURCE_2.format(new Object[] { scope, name, value.getSource() }));
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
            if (!entry.getValue().getScopes().contains(usage))
                continue;
            if (entry.getValue().getAccessCount() == 0)
                os.println(SCOPE_0_SEEMS_TO_BE_UNUSED_FOR_USAGE_1.format(new Object[] { entry.getKey(), usage }));
        }
    }
}
