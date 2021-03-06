/*
 * Copyright (c) 2017 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.services.utils.akka;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.model.base.headers.WithDittoHeaders;
import org.slf4j.Logger;
import org.slf4j.MDC;

import akka.actor.Actor;
import akka.event.DiagnosticLoggingAdapter;
import akka.event.Logging;

/**
 * Utilities for logging.
 */
public final class LogUtil {

    /**
     * Name of the Header for the global Ditto correlation ID.
     */
    public static final String X_CORRELATION_ID = "x-correlation-id";

    /*
     * Inhibit instantiation of this utility class.
     */
    private LogUtil() {
        throw new AssertionError();
    }

    /**
     * Obtain LoggingAdapter with MDC support for the given actor.
     *
     * @param logSource the Actor used as logSource
     * @return the created DiagnosticLoggingAdapter.
     */
    public static DiagnosticLoggingAdapter obtain(final Actor logSource) {
        return Logging.apply(logSource);
    }

    /**
     * Enhances the MDC around the passed SLF4J {@code Logger} with the {@code correlation-id} extracted from the passed
     * {@code withDittoHeaders}. Invokes the passed in {@code logConsumer} around the MDC setting/removing.
     *
     * @param slf4jLogger the SLF4J logger to log with.
     * @param withDittoHeaders where to extract a possible correlation ID from.
     * @param logConsumer the consumer with which the caller must log.
     */
    public static void logWithCorrelationId(final Logger slf4jLogger, final WithDittoHeaders<?> withDittoHeaders,
            final Consumer<Logger> logConsumer) {

        logWithCorrelationId(slf4jLogger, withDittoHeaders.getDittoHeaders(), logConsumer);
    }

    /**
     * Enhances the MDC around the passed SLF4J {@code Logger} with the {@code correlation-id} extracted from the passed
     * {@code withDittoHeaders}. Invokes the passed in {@code logConsumer} around the MDC setting/removing.
     *
     * @param slf4jLogger the SLF4J logger to log with.
     * @param withDittoHeaders where to extract a possible correlation ID from.
     * @param additionalMdcFields additional fields which should bet set to the MDC.
     * @param logConsumer the consumer with which the caller must log.
     */
    public static void logWithCorrelationId(final Logger slf4jLogger,
            final WithDittoHeaders<?> withDittoHeaders,
            final Map<String, String> additionalMdcFields,
            final Consumer<Logger> logConsumer) {

        logWithCorrelationId(slf4jLogger, withDittoHeaders.getDittoHeaders(), additionalMdcFields, logConsumer);
    }

    /**
     * Enhances the MDC around the passed SLF4J {@code Logger} with the {@code correlation-id} extracted from the passed
     * {@code dittoHeaders}. Invokes the passed in {@code logConsumer} around the MDC setting/removing.
     *
     * @param slf4jLogger the SLF4J logger to log with.
     * @param dittoHeaders where to extract a possible correlation ID from.
     * @param logConsumer the consumer with which the caller must log.
     */
    public static void logWithCorrelationId(final Logger slf4jLogger, final DittoHeaders dittoHeaders,
            final Consumer<Logger> logConsumer) {

        logWithCorrelationId(slf4jLogger, dittoHeaders.getCorrelationId(), logConsumer);
    }

    /**
     * Enhances the MDC around the passed SLF4J {@code Logger} with the {@code correlation-id} extracted from the passed
     * {@code dittoHeaders}. Invokes the passed in {@code logConsumer} around the MDC setting/removing.
     *
     * @param slf4jLogger the SLF4J logger to log with.
     * @param dittoHeaders where to extract a possible correlation ID from.
     * @param additionalMdcFields additional fields which should bet set to the MDC.
     * @param logConsumer the consumer with which the caller must log.
     */
    public static void logWithCorrelationId(final Logger slf4jLogger,
            final DittoHeaders dittoHeaders,
            final Map<String, String> additionalMdcFields,
            final Consumer<Logger> logConsumer) {

        logWithCorrelationId(slf4jLogger, dittoHeaders.getCorrelationId(), additionalMdcFields, logConsumer);
    }

    /**
     * Enhances the MDC around the passed SLF4J {@code Logger} with the {@code correlation-id}.
     * Invokes the passed in {@code logConsumer} around the MDC setting/removing.
     *
     * @param slf4jLogger the SLF4J logger to log with.
     * @param correlationId the correlation ID to put onto the MDC.
     * @param logConsumer the consumer with which the caller must log.
     */
    public static void logWithCorrelationId(final Logger slf4jLogger, final Optional<String> correlationId,
            final Consumer<Logger> logConsumer) {

        logWithCorrelationId(slf4jLogger, correlationId.orElse(null), logConsumer);
    }

    /**
     * Enhances the MDC around the passed SLF4J {@code Logger} with the {@code correlation-id}.
     * Invokes the passed in {@code logConsumer} around the MDC setting/removing.
     *
     * @param slf4jLogger the SLF4J logger to log with.
     * @param correlationId the correlation ID to put onto the MDC.
     * @param additionalMdcFields additional fields which should bet set to the MDC.
     * @param logConsumer the consumer with which the caller must log.
     */
    public static void logWithCorrelationId(final Logger slf4jLogger,
            final Optional<String> correlationId,
            final Map<String, String> additionalMdcFields,
            final Consumer<Logger> logConsumer) {

        logWithCorrelationId(slf4jLogger, correlationId.orElse(null), additionalMdcFields, logConsumer);
    }

    /**
     * Enhances the MDC around the passed SLF4J {@code Logger} with the {@code correlation-id}.
     * Invokes the passed in {@code logConsumer} around the MDC setting/removing.
     *
     * @param slf4jLogger the SLF4J logger to log with.
     * @param correlationId the correlation ID to put onto the MDC.
     * @param logConsumer the consumer with which the caller must log.
     */
    public static void logWithCorrelationId(final Logger slf4jLogger, @Nullable final CharSequence correlationId,
            final Consumer<Logger> logConsumer) {

        logWithCorrelationId(slf4jLogger, correlationId, Collections.emptyMap(), logConsumer);
    }

    /**
     * Enhances the MDC around the passed SLF4J {@code Logger} with the {@code correlation-id}.
     * Invokes the passed in {@code logConsumer} around the MDC setting/removing.
     *  @param slf4jLogger the SLF4J logger to log with.
     * @param correlationId the correlation ID to put onto the MDC.
     * @param additionalMdcFields additional fields which should bet set to the MDC.
     * @param logConsumer the consumer with which the caller must log.
     */
    public static void logWithCorrelationId(final Logger slf4jLogger,
            @Nullable final CharSequence correlationId,
            final Map<String, String> additionalMdcFields,
            final Consumer<Logger> logConsumer) {

        final Map<String, String> originalContext = MDC.getCopyOfContextMap();
        if (null != correlationId) {
            MDC.put(X_CORRELATION_ID, correlationId.toString());
        }
        additionalMdcFields.forEach(MDC::put);
        try {
            logConsumer.accept(slf4jLogger);
        } finally {
            if (null != originalContext) {
                MDC.setContextMap(originalContext);
            } else {
                MDC.clear();
            }
        }
    }

    /**
     * Enhances the passed {@link DiagnosticLoggingAdapter} with an "MDC" map entry by extracting a
     * {@code correlationId} of the passed {@code withDittoHeaders} (if present).
     *
     * @param loggingAdapter the DiagnosticLoggingAdapter to set the "MDC" on.
     * @param withDittoHeaders where to extract a possible correlation ID from.
     */
    public static void enhanceLogWithCorrelationId(final DiagnosticLoggingAdapter loggingAdapter,
            final WithDittoHeaders<?> withDittoHeaders) {

        loggingAdapter.clearMDC();
        withDittoHeaders.getDittoHeaders()
                .getCorrelationId()
                .ifPresent(s -> enhanceLogWithCorrelationId(loggingAdapter, s));
    }

    /**
     * Enhances the passed {@link DiagnosticLoggingAdapter} with an "MDC" map entry by extracting a
     * {@code correlationId} of the passed {@code dittoHeaders} (if present).
     *
     * @param loggingAdapter the DiagnosticLoggingAdapter to set the "MDC" on.
     * @param dittoHeaders where to extract a possible correlation ID from.
     */
    public static void enhanceLogWithCorrelationId(final DiagnosticLoggingAdapter loggingAdapter,
            final DittoHeaders dittoHeaders) {

        loggingAdapter.clearMDC();
        dittoHeaders.getCorrelationId().ifPresent(s -> enhanceLogWithCorrelationId(loggingAdapter, s));
    }

    /**
     * Enhances the passed {@link DiagnosticLoggingAdapter} with an "MDC" map entry for the passed {@code correlationId}
     * (if present).
     *
     * @param loggingAdapter the DiagnosticLoggingAdapter to set the "MDC" on.
     * @param correlationId the optional correlation ID to set.
     */
    public static void enhanceLogWithCorrelationId(final DiagnosticLoggingAdapter loggingAdapter,
            final Optional<String> correlationId) {

        loggingAdapter.clearMDC();
        correlationId.ifPresent(s -> enhanceLogWithCorrelationId(loggingAdapter, s));
    }

    /**
     * Enhances the passed {@link DiagnosticLoggingAdapter} with an "MDC" map entry for the passed {@code correlationId}
     * (if present).
     *
     * @param loggingAdapter the DiagnosticLoggingAdapter to set the "MDC" on.
     * @param correlationId the optional correlation ID to set.
     */
    public static void enhanceLogWithCorrelationId(final DiagnosticLoggingAdapter loggingAdapter,
            final CharSequence correlationId) {

        loggingAdapter.clearMDC();
        if (null != correlationId && 0 < correlationId.length()) {
            final Map<String, Object> mdcMap = new HashMap<>();
            mdcMap.put(X_CORRELATION_ID, correlationId);
            loggingAdapter.setMDC(mdcMap);
        }
    }

    /**
     * Enhances the passed {@link DiagnosticLoggingAdapter} with an "MDC" map entry for the passed {@code fieldName}
     * with the passed {@code fieldValue} (if present).
     *
     * @param loggingAdapter the DiagnosticLoggingAdapter to set the "MDC" on.
     * @param fieldName the field value to set in MDC.
     * @param fieldValue the optional value to set.
     */
    public static void enhanceLogWithCustomField(final DiagnosticLoggingAdapter loggingAdapter,
            final String fieldName, @Nullable final String fieldValue) {

        if (null != fieldValue && !fieldValue.isEmpty()) {
            final Map<String, Object> mdcMap = new HashMap<>(loggingAdapter.getMDC());
            mdcMap.put(fieldName, fieldValue);
            loggingAdapter.setMDC(mdcMap);
        }
    }

    /**
     * Enhances the default slf4j {@link org.slf4j.MDC} with a {@code correlationId} of the passed
     * {@code withDittoHeaders} (if present).
     *
     * @param withDittoHeaders where to extract a possible correlation ID from.
     */
    public static void enhanceLogWithCorrelationId(final WithDittoHeaders<?> withDittoHeaders) {
        withDittoHeaders.getDittoHeaders()
                .getCorrelationId()
                .ifPresent(LogUtil::enhanceLogWithCorrelationId);
    }

    /**
     * Enhances the default slf4j {@link org.slf4j.MDC} with a {@code correlationId} of the passed
     * {@code dittoHeaders} (if present).
     *
     * @param dittoHeaders where to extract a possible correlation ID from.
     */
    public static void enhanceLogWithCorrelationId(final DittoHeaders dittoHeaders) {
        dittoHeaders.getCorrelationId().ifPresent(LogUtil::enhanceLogWithCorrelationId);
    }

    /**
     * Enhances the default slf4j {@link org.slf4j.MDC} with a {@code correlationId} of the passed
     * {@code withDittoHeaders} (if present), else a random correlation ID.
     *
     * @param withDittoHeaders where to extract a possible correlation ID from.
     */
    public static void enhanceLogWithCorrelationIdOrRandom(final WithDittoHeaders<?> withDittoHeaders) {
        LogUtil.enhanceLogWithCorrelationIdOrRandom(withDittoHeaders.getDittoHeaders());
    }

    /**
     * Enhances the default slf4j {@link org.slf4j.MDC} with a {@code correlationId} of the passed
     * {@code dittoHeaders} (if present), else a random correlation ID.
     *
     * @param dittoHeaders where to extract a possible correlation ID from.
     */
    public static void enhanceLogWithCorrelationIdOrRandom(final DittoHeaders dittoHeaders) {
        LogUtil.enhanceLogWithCorrelationId(
                dittoHeaders.getCorrelationId().orElseGet(() -> UUID.randomUUID().toString()));
    }

    /**
     * Enhances the default slf4j {@link org.slf4j.MDC} with a {@code correlationId}.
     *
     * @param correlationId the optional correlation ID to set.
     */
    public static void enhanceLogWithCorrelationId(final CharSequence correlationId) {
        if (null != correlationId && 0 < correlationId.length()) {
            MDC.put(X_CORRELATION_ID, correlationId.toString());
        }
    }

    /**
     * Gets the {@code correlationId} from the default slf4j {@link org.slf4j.MDC}.
     *
     * @return the {@code correlationId} from {@link org.slf4j.MDC} or an empty {@link java.util.Optional} if it didn't exist.
     */
    public static Optional<String> getCorrelationId() {
        final String correlationId = MDC.get(X_CORRELATION_ID);
        if (null != correlationId) {
            return Optional.of(correlationId);
        }
        return Optional.empty();
    }

    /**
     * Gets the {@code correlationId} from the default slf4j {@link org.slf4j.MDC}.
     *
     * @param defaultCorrelationIdSupplier supplies a default correlation ID if none could be found inside {@link org.slf4j.MDC}.
     * @return The {@code correlationId} from {@link org.slf4j.MDC} or from {@code defaultCorrelationIdSupplier} if it didn't exist.
     */
    public static String getCorrelationId(final Supplier<String> defaultCorrelationIdSupplier) {
        return getCorrelationId().orElseGet(defaultCorrelationIdSupplier);
    }

    /**
     * Removes the {@code correlationId} from the default slf4j {@link org.slf4j.MDC}.
     */
    public static void removeCorrelationId() {
        MDC.remove(X_CORRELATION_ID);
    }

}
