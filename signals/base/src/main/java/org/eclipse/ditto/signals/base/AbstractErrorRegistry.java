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
package org.eclipse.ditto.signals.base;

import static org.eclipse.ditto.model.base.common.ConditionChecker.checkNotNull;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.json.JsonMissingFieldException;
import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.model.base.exceptions.DittoRuntimeException;

/**
 * Abstract implementation of {@link ErrorRegistry}.
 */
@Immutable
public abstract class AbstractErrorRegistry<T extends DittoRuntimeException> extends AbstractJsonParsableRegistry<T>
        implements ErrorRegistry<T> {

    /**
     * Constructs a new {@code AbstractErrorRegistry} for the specified {@code parseStrategies}.
     *
     * @param parseStrategies the parse strategies.
     */
    protected AbstractErrorRegistry(final Map<String, JsonParsable<T>> parseStrategies) {
        super(parseStrategies);
    }

    /**
     * Constructs a new {@code AbstractErrorRegistry} for the specified {@code parseStrategies}.
     *
     * @param parseStrategies the parse strategies.
     * @param fallbackErrorRegistry the {@link ErrorRegistry} to be used as fallback
     * @throws NullPointerException if any argument is {@code null}.
     */
    protected AbstractErrorRegistry(final Map<String, JsonParsable<T>> parseStrategies,
            final ErrorRegistry<T> fallbackErrorRegistry) {

        super(mergeParseRegistries(parseStrategies, fallbackErrorRegistry));
    }

    private static <T extends DittoRuntimeException> Map<String, JsonParsable<T>> mergeParseRegistries(
            final Map<String, JsonParsable<T>> parseStrategies, final ErrorRegistry<T> errorRegistry) {

        checkNotNull(parseStrategies, "parse strategies");
        checkNotNull(errorRegistry, "fallback error registry");

        final Map<String, JsonParsable<T>> mergedParseStrategies = new HashMap<>(parseStrategies);

        errorRegistry.getTypes().forEach(type -> mergedParseStrategies.put(type, errorRegistry::parse));

        return mergedParseStrategies;
    }

    @Override
    protected String resolveType(final JsonObject jsonObject) {
        return jsonObject.getValue(DittoRuntimeException.JsonFields.ERROR_CODE)
                .orElseThrow(() -> JsonMissingFieldException.newBuilder()
                        .fieldName(DittoRuntimeException.JsonFields.ERROR_CODE.getPointer().toString())
                        .build());
    }

}
