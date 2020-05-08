/*
 * Copyright (c) 2018, 2020 Oracle and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package by.zloy;

import io.helidon.microprofile.server.Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.LogManager;

/**
 * The application main class.
 */
public final class MainApplication {

    /**
     * Cannot be instantiated.
     */
    private MainApplication() {
    }

    /**
     * Application main entry point.
     *
     * @param args command line arguments
     * @throws IOException if there are problems reading logging properties
     */
    public static void main(final String[] args) throws IOException {
        // load logging configuration
        setupLogging();

        // start the server
        startServer();
    }

    /**
     * Start the server.
     */
    static Server startServer() {
        // Server will automatically pick up configuration from microprofile-config.properties
        // and Application classes annotated as @ApplicationScoped
        return Server.create().start();
    }

    /**
     * Configure logging from logging.properties file.
     */
    private static void setupLogging() throws IOException {
        try (InputStream is = MainApplication.class.getResourceAsStream("/logging.properties")) {
            LogManager.getLogManager().readConfiguration(is);
        }
    }
}
