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
package org.eclipse.ditto.model.thingsearchparser;

import java.util.List;

import org.eclipse.ditto.model.thingsearch.Option;
import org.eclipse.ditto.model.thingsearchparser.internal.RqlOptionParser$;

/**
 * RQL Parser parsing options in the RQL "standard" according to https://github.com/persvr/rql.
 */
public final class RqlOptionParser implements OptionParser {

    private static final OptionParser PARSER = RqlOptionParser$.MODULE$;

    @Override
    public List<Option> parse(final String input) {
        return PARSER.parse(input);
    }
}
