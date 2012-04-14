package org.conf4j.maven;

import static org.conf4j.base.dsl.EUsage.undefined;

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
import org.conf4j.base.ConfElements;
import org.conf4j.base.dsl.Conf4j;
import org.conf4j.base.dsl.EUsage;

/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
/**
 * @goal generate
 * @phase install
 * @threadSafe
 */
public final class Conf4jMojo extends AbstractMojo {
    private static final MessageFormat VARIABLE_0_VALUE_1_DESCRIPTION_2 = new MessageFormat("## {2}\n{0}={1}\n");
    private static final MessageFormat conf4j_0_dot_properties = new MessageFormat("conf4j_{0}.properties");
    /**
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private File outputDirectory;

    /**
     * @parameter expression="undefined"
     * @required
     */
    private String usage;

    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!outputDirectory.exists())
            throw new MojoFailureException(outputDirectory + " does not directory");
        if (!outputDirectory.isDirectory())
            throw new MojoFailureException(outputDirectory + " is not a directory");

        final EUsage targetUsage;
        try {
            targetUsage = EUsage.valueOf(usage);
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
            final List<EUsage> usages = Arrays.asList(annotation.usage());
            final boolean devPurposeOnly = annotation.devPurposeOnly();
            if (!devPurposeOnly)
                continue;
            if (!usages.contains(targetUsage) && !(usages.size() == 1 && usages.contains(undefined)))
                continue;
            buffer.append(VARIABLE_0_VALUE_1_DESCRIPTION_2.format(new Object[] { field.getName(), value, description }));
        }
        final File file = new File(outputDirectory, conf4j_0_dot_properties.format(new Object[] { targetUsage }));
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
