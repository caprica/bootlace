/*
 * This file is part of Bootlace.
 *
 * Copyright (C) 2015
 * Caprica Software Limited (capricasoftware.co.uk)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
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

package uk.co.caprica.bootlace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Standard Spring Boot Application launcher class.
 * <p>
 * This class is used to launch the application and to configure various application components.
 * <p>
 * Configuration values are pulled from a standard Spring Boot "application.properties" file.
 */
@SpringBootApplication
public class BootlaceApplication {

    /**
     * Log.
     */
    private final Logger logger = LoggerFactory.getLogger(BootlaceApplication.class);

    /**
     * Application property to enable/disable pretty-printing of the JSON returned by the
     * web-services.
     * <p>
     * A default value of true is provided.
     */
    @Value("${app.prettyJson:true}")
    private boolean prettyJson;

    /**
     * Application property to set the password encoder strength.
     * <p>
     * A default value of -1 is provided to signify that the the encoder default strength should be
     * used.
     */
    @Value("${app.passwordEncoderStrength:-1}")
    private int passwordEncoderStrength;

    /**
     * Application entry-point.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(BootlaceApplication.class, args);
    }

    /**
     * Configure the Jackson JSON {@link ObjectMapper} bean.
     * <p>
     * This configuration optionally enables pretty-printing of the generated JSON.
     *
     * @return object-mapper component
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        logger.debug("prettyJson={}", prettyJson);
        if (prettyJson) {
            objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        }
        return objectMapper;
    }

    /**
     * Configure a password encoder to use when creating new user accounts.
     *
     * @return password encoder component
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(passwordEncoderStrength);
    }

}
