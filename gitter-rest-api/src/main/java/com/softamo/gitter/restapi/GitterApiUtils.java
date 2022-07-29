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
import io.micronaut.http.uri.UriBuilder;

import java.net.URI;

/**
 * Utility classes to build Gitter API Uris.
 * @author Sergio del Amo
 * @since 1.0.0
 */
public final class GitterApiUtils {
    private GitterApiUtils() {

    }

    /**
     *
     * @param roomId ROOM ID
     * @return /v1/rooms/{roomId}/chatMessages URI
     */
    @NonNull
    public static URI chatMessagesUri(@NonNull String roomId) {
        return roomsUriBuilder()
                .path(roomId)
                .path("chatMessages")
                .build();
    }

    /**
     *
     * @return Builds /v1/rooms URI Builder
     */
    public static UriBuilder roomsUriBuilder() {
        return UriBuilder.of("/v1").path("rooms");
    }
}
