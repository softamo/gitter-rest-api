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

import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.async.annotation.SingleResult;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import org.reactivestreams.Publisher;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Micronaut declarative HTTP Client for Gitter REST API.
 */
@Client(
        value = "${" + GitterClientConfiguration.PREFIX + ".url:`" + GitterClientConfiguration.HOST_LIVE + "`}",
        configuration = GitterClientConfiguration.class
)
@Retryable(
        attempts = "${" + GitterClientConfiguration.PREFIX + ".retry.attempts:0}",
        delay = "${" + GitterClientConfiguration.PREFIX + ".retry.delay:5s}")
public interface GitterClient {
    /**
     *
     * @param authorization Bearer Token
     * @param roomId Room Id
     * @param message Message
     * @return Message Response
     */
    @Post("/v1/rooms/{roomId}/chatMessages")
    @SingleResult
    Publisher<MessageResponse> sendMessage(@Header(HttpHeaders.AUTHORIZATION) String authorization,
                                @PathVariable @NonNull @NotBlank String roomId,
                                @NonNull @NotNull @Valid @Body Message message);

    /**
     *
     * @param authorization Bearer Token
     * @return List of Rooms
     */
    @Get("/v1/rooms")
    @SingleResult
    Publisher<List<Room>> findAllRooms(@Header(HttpHeaders.AUTHORIZATION) String authorization);
}
