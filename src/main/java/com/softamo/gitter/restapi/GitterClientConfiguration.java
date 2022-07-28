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

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.http.client.HttpClientConfiguration;
import io.micronaut.runtime.ApplicationConfiguration;

import javax.validation.constraints.NotBlank;
import java.time.Duration;

/**
 *  {@link HttpClientConfiguration} through a {@link ConfigurationProperties} for {@link GitterClient}.
 * @author Sergio del Amo
 * @since 1.0.0
 */
@Requires(property = GitterClientConfiguration.PREFIX + ".token")
@ConfigurationProperties(GitterClientConfiguration.PREFIX)
public class GitterClientConfiguration extends HttpClientConfiguration implements GitterConfiguration {
    /**
     * Configuration prefix.
     */
    public static final String PREFIX = "gitter";
    /**
     * Gitter API URL.
     */
    public static final String HOST_LIVE = "https://api.gitter.im/";

    @NonNull
    @NotBlank
    private String token;

    /**
     * Gitter Connection pool configuration.
     */
    private final GitterConnectionPoolConfiguration connectionPoolConfiguration;

    @NonNull
    @NotBlank
    private String url = HOST_LIVE;

    /**
     *
     * @param applicationConfiguration Application Configuration
     * @param connectionPoolConfiguration Gitter Connection pool configuration.
     */
    public GitterClientConfiguration(final ApplicationConfiguration applicationConfiguration,
                                  final GitterConnectionPoolConfiguration connectionPoolConfiguration) {
        super(applicationConfiguration);
        this.connectionPoolConfiguration = connectionPoolConfiguration;
    }

    /**
     *
     * @return URL
     */
    @NotBlank
    @NonNull
    public String getUrl() {
        return this.url;
    }

    /**
     *
     * @param url Sets Gitter API URL. Useful to override url for testing.
     */
    public void setUrl(@NonNull @NotBlank String url) {
        this.url = url;
    }

    @Override
    @NonNull
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token Gitter Token
     */
    public void setToken(@NonNull String token) {
        this.token = token;
    }

    @Override
    @NonNull
    public ConnectionPoolConfiguration getConnectionPoolConfiguration() {
        return this.connectionPoolConfiguration;
    }

    /**
     * {@link ConnectionPoolConfiguration} for {@link GitterClient}.
     */
    @ConfigurationProperties(ConnectionPoolConfiguration.PREFIX)
    public static class GitterConnectionPoolConfiguration extends ConnectionPoolConfiguration {
    }

    /**
     * Extra {@link ConfigurationProperties} to set the values for the {@link io.micronaut.retry.annotation.Retryable} annotation on {@link GitterClient}.
     */
    @ConfigurationProperties(GitterConnectionPoolRetryConfiguration.PREFIX)
    public static class GitterConnectionPoolRetryConfiguration {

        /**
         * prefix.
         */
        public static final String PREFIX = "retry";

        private static final Duration DEFAULT_DELAY = Duration.ofSeconds(5);
        private static final int DEFAULT_ATTEMPTS = 0;

        /**
         * The delay between retry attempts.
         */
        private Duration delay = DEFAULT_DELAY;

        /**
         * The maximum number of retry attempts.
         */
        private int attempts = DEFAULT_ATTEMPTS;

        /**
         *
         * @return Delay between attempts
         */
        public Duration getDelay() {
            return delay;
        }

        /**
         *
         * @param delay delay between attemps
         */
        public void setDelay(Duration delay) {
            this.delay = delay;
        }

        /**
         *
         * @return Number of Attempts
         */
        public int getAttempts() {
            return attempts;
        }

        /**
         *
         * @param attempts Number of attempts
         */
        public void setAttempts(int attempts) {
            this.attempts = attempts;
        }
    }
}

