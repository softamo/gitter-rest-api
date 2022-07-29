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

import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(startApplication = false)
class MessageResponseTest {

    @Inject
    Validator validator;

    @Test
    void isAnnotatedWithIntrospected() {
        assertDoesNotThrow(() -> BeanIntrospection.getIntrospection(MessageResponse.class));
    }

    @Test
    void idCannotBeBlank() {
        assertFalse(validator.validate(new MessageResponse("", "Hello World")).isEmpty());
        assertFalse(validator.validate(new MessageResponse(null, "Hello World")).isEmpty());
    }

    @Test
    void textCannotBeBlank() {
        assertFalse(validator.validate(new MessageResponse("62e106cc0a522647989c5708", "")).isEmpty());
        assertFalse(validator.validate(new MessageResponse("62e106cc0a522647989c5708", null)).isEmpty());
    }
}
