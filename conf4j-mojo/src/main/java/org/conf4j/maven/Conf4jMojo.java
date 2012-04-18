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
package org.conf4j.maven;

import static org.conf4j.EScope.undefined;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.conf4j.Conf4j;
import org.conf4j.ConfElements;
import org.conf4j.EScope;

/**
 * @goal generate
 * @phase install
 * @threadSafe
 */
public final class Conf4jMojo extends AbstractMojo {
    private static final MessageFormat VARIABLE_0_VALUE_1_DESCRIPTION_2 = new MessageFormat("## {2}\n{0}={1}\n");
    /**
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private File outputDirectory;

    /**
     * @parameter expression="undefined"
     * @required
     */
    private String scope;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!outputDirectory.exists())
            throw new MojoFailureException(outputDirectory + " does not directory");
        if (!outputDirectory.isDirectory())
            throw new MojoFailureException(outputDirectory + " is not a directory");

        final EScope targetScope;
        try {
            targetScope = EScope.valueOf(scope);
        } catch (IllegalArgumentException e) {
            throw new MojoFailureException(e.getMessage());
        }

        final StringBuffer buffer = new StringBuffer();
        for (Field field : ConfElements.class.getDeclaredFields()) {
            final Conf4j annotation = field.getAnnotation(Conf4j.class);
            if (annotation == null)
                continue;
            final String value = annotation.value();
            final String description = annotation.description();
            final List<EScope> usages = Arrays.asList(annotation.scope());
            final boolean devPurposeOnly = annotation.devPurposeOnly();
            if (!devPurposeOnly)
                continue;
            if (!usages.contains(targetScope) && !(usages.size() == 1 && usages.contains(undefined)))
                continue;
            buffer.append(VARIABLE_0_VALUE_1_DESCRIPTION_2.format(new Object[] { field.getName(), value, description }));
        }
        final File file = new File(outputDirectory, "conf4j.properties");
        try {
            IOUtils.write(buffer, new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            throw new MojoFailureException(e.getMessage());
        } catch (IOException e) {
            throw new MojoFailureException(e.getMessage());
        } finally {
            getLog().info("written: " + file);
        }
    }

}
