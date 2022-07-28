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

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

/**
 * Gitter Message Response.
 * @author Sergio del Amo
 * @since 1.0.0
 */
@Introspected
public class MessageResponse {
    @NonNull
    @NotBlank
    private String id;

    @NonNull
    @NotBlank
    private String text;

    /**
     * Constructor.
     */
    public MessageResponse() {
    }

    /**
     *
     * @param id Identifier
     * @param text text
     */
    @Creator
    public MessageResponse(@NonNull String id,
                           @NonNull String text) {
        this.id = id;
        this.text = text;
    }

    /**
     *
     * @return Message Response ID
     */
    @NonNull
    public String getId() {
        return id;
    }

    /**
     *
     * @return Message response text
     */
    @NonNull
    public String getText() {
        return text;
    }

    /**
     *
     * @param id Message ID
     */
    public void setId(@NonNull String id) {
        this.id = id;
    }

    /**
     *
     * @param text Message Text
     */
    public void setText(@NonNull String text) {
        this.text = text;
    }
}
