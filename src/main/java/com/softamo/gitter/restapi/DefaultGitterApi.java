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

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpHeaderValues;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Default implementation of {@link GitterApi}.
 */
@Requires(bean = GitterClientConfiguration.class)
@Singleton
public class DefaultGitterApi implements GitterApi {
    private final GitterClient gitterClient;
    private final String token;

    /**
     *
     * @param gitterConfiguration Gitter Configuration
     * @param gitterClient Gitter Client
     */
    public DefaultGitterApi(GitterConfiguration gitterConfiguration,
                            GitterClient gitterClient) {
        this.token = HttpHeaderValues.AUTHORIZATION_PREFIX_BEARER + " " + gitterConfiguration.getToken();
        this.gitterClient = gitterClient;
    }

    @Override
    @NonNull
    @SingleResult
    public Publisher<MessageResponse> sendMessage(@NonNull @NotBlank String roomId,
                                                  @NonNull @NotNull @Valid Message message) {
        return gitterClient.sendMessage(token, roomId, message);
    }

    @Override
    @NonNull
    @SingleResult
    public Publisher<List<Room>> findAllRooms() {
        return gitterClient.findAllRooms(token);
    }
}
