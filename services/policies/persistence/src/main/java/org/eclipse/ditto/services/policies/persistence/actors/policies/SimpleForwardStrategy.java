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
package org.eclipse.ditto.services.policies.persistence.actors.policies;

import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.ditto.services.policies.persistence.actors.policy.PolicyPersistenceActor;
import org.eclipse.ditto.signals.commands.policies.PolicyCommand;

import akka.actor.ActorContext;
import akka.event.DiagnosticLoggingAdapter;

/**
 * This strategy simply forwards a {@link PolicyCommand} to the appropriate {@link PolicyPersistenceActor}. The correct
 * Actor is determined by the Policy ID. If no appropriate Actor can be found the sender of the command is told
 * that the Policy cannot be found.
 *
 * @param <T> type of the class this strategy matches against.
 */
@NotThreadSafe
final class SimpleForwardStrategy<T extends PolicyCommand> extends AbstractReceivePolicyCommandStrategy<T> {

    /**
     * Constructs a new {@code SimpleForwardStrategy} object.
     *
     * @param matchingClass the class of the command this strategy reacts to.
     * @param context the Actor context.
     * @param loggingAdapter the adapter which is used for logging. {@link PolicyPersistenceActor}s.
     */
    SimpleForwardStrategy(final Class<T> matchingClass, final ActorContext context,
            final DiagnosticLoggingAdapter loggingAdapter) {
        super(matchingClass, context, loggingAdapter);
    }

    @Override
    protected void doApply(final T command, final ActorContext ctx) {
        forwardToPolicyPersistenceActor(command);
    }
}
