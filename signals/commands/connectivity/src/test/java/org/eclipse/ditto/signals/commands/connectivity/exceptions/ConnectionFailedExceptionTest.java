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
package org.eclipse.ditto.signals.commands.connectivity.exceptions;

import static org.eclipse.ditto.model.base.assertions.DittoBaseAssertions.assertThat;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import org.eclipse.ditto.json.JsonFactory;
import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.junit.Test;

/**
 * Tests {@link ConnectionFailedException}.
 */
public final class ConnectionFailedExceptionTest {


    @Test
    public void assertImmutability() {
        assertInstancesOf(ConnectionFailedException.class, areImmutable());
    }

    @Test
    public void checkToJsonFromJson() {
        final ConnectionFailedException failedException = ConnectionFailedException.newBuilder("id")
                .message("message")
                .description("description")
                .build();

        final String jsonString = failedException.toJsonString();

        final ConnectionFailedException failedExceptionFromJson =
                ConnectionFailedException.fromJson(JsonFactory.readFrom(jsonString).asObject(), DittoHeaders.empty());

        System.out.println(failedException.toJsonString());
        System.out.println(failedExceptionFromJson.toJsonString());

        assertThat(failedExceptionFromJson.toJsonString()).isEqualTo(failedException.toJsonString());
    }

}
