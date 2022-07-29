/*
 * Copyright 2017-2022 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.softamo.gitter.restapi;

import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.BlockingHttpClient;
import io.micronaut.http.client.HttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.softamo.gitter.restapi.GitterApiUtils.chatMessagesUri;
import static com.softamo.gitter.restapi.GitterApiUtils.roomsUriBuilder;

/**
 * Manual implementation of {@link BlockingGitterClient}.
 */
public class ManualBlockingGitterClient implements BlockingGitterClient {

    private final BlockingHttpClient httpClient;

    /**
     * Constructor.
     */
    public ManualBlockingGitterClient() {
        this(GitterClientConfiguration.HOST_LIVE);
    }

    /**
     * Constructor.
     * @param url Gitter API URL
     * @throws ConfigurationException if the URL is malformed
     */
    public ManualBlockingGitterClient(String url) {
        try {
            httpClient = HttpClient.create(new URL(url)).toBlocking();
        } catch (MalformedURLException e) {
            throw new ConfigurationException("malformed url " + url);
        }
    }

    @Override
    public MessageResponse sendMessage(String authorization, String roomId, Message message) {
        return httpClient.retrieve(HttpRequest.POST(chatMessagesUri(roomId), message)
                .header(HttpHeaders.AUTHORIZATION, authorization), MessageResponse.class);
    }

    @Override
    public List<Room> findAllRooms(String authorization) {
        return httpClient.retrieve(HttpRequest.GET(roomsUriBuilder().build())
                .header(HttpHeaders.AUTHORIZATION, authorization), Argument.listOf(Room.class));
    }
}

