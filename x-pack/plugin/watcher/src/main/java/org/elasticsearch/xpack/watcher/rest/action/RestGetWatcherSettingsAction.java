/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.watcher.rest.action;

import org.elasticsearch.client.internal.node.NodeClient;
import org.elasticsearch.rest.BaseRestHandler;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.RestUtils;
import org.elasticsearch.rest.action.RestCancellableNodeClient;
import org.elasticsearch.rest.action.RestToXContentListener;
import org.elasticsearch.xpack.core.watcher.transport.actions.put.GetWatcherSettingsAction;

import java.io.IOException;
import java.util.List;

import static org.elasticsearch.rest.RestRequest.Method.GET;

/**
 * Allows retrieving a subset of index settings (those use-settable) for the .watches index.
 * See {@link RestUpdateWatcherSettingsAction} for the setting counterpart.
 */
public class RestGetWatcherSettingsAction extends BaseRestHandler {
    @Override
    public String getName() {
        return "watcher_get_settings";
    }

    @Override
    public List<Route> routes() {
        return List.of(new Route(GET, "/_watcher/settings"));
    }

    @Override
    protected RestChannelConsumer prepareRequest(RestRequest request, NodeClient client) throws IOException {
        GetWatcherSettingsAction.Request req = new GetWatcherSettingsAction.Request(RestUtils.getMasterNodeTimeout(request));
        return channel -> new RestCancellableNodeClient(client, request.getHttpChannel()).execute(
            GetWatcherSettingsAction.INSTANCE,
            req,
            new RestToXContentListener<>(channel)
        );
    }
}
