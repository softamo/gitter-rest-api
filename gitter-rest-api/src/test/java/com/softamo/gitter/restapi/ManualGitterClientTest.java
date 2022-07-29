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

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.annotation.Property;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import io.micronaut.runtime.server.EmbeddedServer;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.test.support.TestPropertyProvider;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.function.Executable;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Property(name = "spec.name", value = "ManualGitterClientTest")
@MicronautTest(startApplication = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ManualGitterClientTest implements TestPropertyProvider {

    EmbeddedServer gitterApi;

    @Override
    public Map<String, String> getProperties() {
        return Collections.singletonMap("gitter.url", gitterUrl());
    }

    public String gitterUrl() {
        if (gitterApi == null) {
            gitterApi = ApplicationContext.run(EmbeddedServer.class, Collections.singletonMap("spec.name", "ManualGitterClientTestGitterApi"));
        }
        return "http://localhost:" + gitterApi.getPort();
    }

    @AfterAll
    public void teardown() {
        gitterApi.close();
    }

    @Test
    void malformedUrlTriggersAConfigurationException() {
        Executable executable = () -> new ManualGitterClient("bloagab.123");
        assertThrows(ConfigurationException.class, executable);
    }

    @Test
    void noArgConstructorThrowsNoException() {
        Executable executable = () -> new ManualGitterClient();
        assertDoesNotThrow(executable);
    }

    @Test
    void testMessageSend() {

        GitterApi blockingGitterApi = new DefaultGitterApi(() -> "xxxx", new ManualGitterClient(gitterUrl()));

        String roomId = "62e103046da03739849a93b8";
        Message message = new Message("hello world");
        List<Room> roomList = Mono.from(blockingGitterApi.findAllRooms()).block();
        assertEquals(1, roomList.size());
        assertEquals("62e103046da03739849a93b8", roomList.get(0).getId());
        assertEquals("Micronaut Framework/test-api-notifications", roomList.get(0).getName());
        MessageResponse messageResponse = Mono.from(blockingGitterApi.sendMessage(roomId, message)).block();
        assertEquals("62e106cc0a522647989c5708", messageResponse.getId());
        assertEquals("hello world", messageResponse.getText());
        messageResponse = Mono.from(blockingGitterApi.sendMessage(roomId, "hello world")).block();
        assertEquals("62e106cc0a522647989c5708", messageResponse.getId());
        assertEquals("hello world", messageResponse.getText());
    }

    @Requires(property = "spec.name", value = "ManualGitterClientTestGitterApi")
    @Controller
    static class RoomsController {
        @Get("/v1/rooms")
        String index() {
            return "[{\"id\":\"62e103046da03739849a93b8\",\"name\":\"Micronaut Framework/test-api-notifications\",\"topic\":\"\",\"avatarUrl\":\"https://avatars-04.gitter.im/group/iv/1/5b03d4e7d73408ce4f9a3d03\",\"uri\":\"micronautfw/test-api-notifications\",\"oneToOne\":false,\"userCount\":2,\"unreadItems\":0,\"mentions\":0,\"lastAccessTime\":\"2022-07-27T09:19:47.647Z\",\"lurk\":false,\"url\":\"/micronautfw/test-api-notifications\",\"githubType\":\"REPO_CHANNEL\",\"security\":\"PRIVATE\",\"noindex\":false,\"tags\":[],\"permissions\":{\"admin\":true},\"roomMember\":true,\"groupId\":\"5b03d4e7d73408ce4f9a3d03\",\"public\":false}]";
        }

        @Post("/v1/rooms/{roomId}/chatMessages")
        String sendMessage(@Header(HttpHeaders.AUTHORIZATION) String authorization,
                           @PathVariable @NonNull @NotBlank String roomId,
                           @NonNull @NotNull @Valid @Body Message message) {
            return "{\"id\":\"62e106cc0a522647989c5708\",\"text\":\"hello world\",\"html\":\"hello world\",\"sent\":\"2022-07-27T09:35:08.966Z\",\"unread\":false,\"readBy\":0,\"urls\":[],\"mentions\":[],\"issues\":[],\"meta\":[],\"v\":1,\"fromUser\":{\"id\":\"5afc44a8d73408ce4f998e67\",\"username\":\"sdelamo_twitter\",\"displayName\":\"Sergio del Amo\",\"url\":\"/sdelamo_twitter\",\"avatarUrl\":\"https://avatars-04.gitter.im/g/u/sdelamo_twitter\",\"avatarUrlSmall\":\"https://pbs.twimg.com/profile_images/678310344528392192/a7KpCrHF_bigger.jpg\",\"avatarUrlMedium\":\"https://pbs.twimg.com/profile_images/678310344528392192/a7KpCrHF.jpg\",\"v\":12}}";
        }
    }
}
