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
package org.eclipse.ditto.services.utils.cluster;

import static org.eclipse.ditto.model.base.common.ConditionChecker.checkNotNull;

import java.util.Set;

import org.eclipse.ditto.services.utils.akka.LogUtil;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.event.DiagnosticLoggingAdapter;
import akka.japi.pf.ReceiveBuilder;

/**
 * Listens to events via PubSub and invokes the abstract {@link #handleEvents()} for each received event.
 */
public abstract class AbstractPubSubListenerActor extends AbstractActor {

    private final DiagnosticLoggingAdapter log = LogUtil.obtain(this);

    /**
     * Creates a cache updater.
     *
     * @param pubSubMediator the DistributedPubSubMediator for registering for events.
     * @param eventTopics the event topics to register for.
     * @param instanceIndex index of this service instance.
     */
    protected AbstractPubSubListenerActor(final ActorRef pubSubMediator,
            final Set<String> eventTopics,
            final String instanceIndex) {
        checkNotNull(eventTopics, "Event Topics");

        final String group = getSelf().path().name() + "-" +instanceIndex;
        eventTopics.forEach(topic ->{
            log.info("Subscribing for pub/sub topic <{}> with group <{}>", topic, group);
            pubSubMediator.tell(subscribe(topic, group), getSelf());
        });
    }

    @Override
    public Receive createReceive() {
        return handleEvents().orElse(ReceiveBuilder.create()
                .match(DistributedPubSubMediator.SubscribeAck.class, subscribeAck ->
                        log.debug("Got SubscribeAck about topic <{}> for group <{}>",
                                subscribeAck.subscribe().topic(),
                                subscribeAck.subscribe().group()))
                .matchAny(message -> {
                    log.warning("Unhandled message <{}>", message);
                    unhandled(message);
                })
                .build());
    }

    /**
     * Return a partial function to handle events this actor subscribes to.
     *
     * @return a partial function.
     */
    protected abstract Receive handleEvents();

    private DistributedPubSubMediator.Subscribe subscribe(final String topic, final String group) {
        return new DistributedPubSubMediator.Subscribe(topic, group, getSelf());
    }

}
