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

package uk.co.caprica.bootlace.controller.profile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import uk.co.caprica.bootlace.data.mongo.profile.ProfileRepository;
import uk.co.caprica.bootlace.domain.profile.Profile;
import uk.co.caprica.bootlace.security.domain.UserWithId;

/**
 * Web-service controller for Profile resources.
 * <p>
 * A profile is basic personal data associated with an application user.
 * <p>
 * {@link RestController} adds {@link Controller} and {@link ResponseBody} by default.
 */
@RestController
@RequestMapping("profile")
public class ProfileController {

    /**
     * Log.
     */
    private final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    /**
     * Repository containing Profile resources.
     */
    @Autowired
    private ProfileRepository profileRepository;

    /**
     * Get the currently authenticated user profile resource.
     * <p>
     * Semantically a user profile (for a valid user) always "exists" even if it has not been
     * created in the repository yet.
     * <p>
     * This is <em>not</em> typical.
     * <p>
     * The security constraints are such that a profile will be returned only for the currently
     * authenticated user, i.e. this service does not allow for a particular profile to be
     * retrieved even if its unique identifier is known.
     * <p>
     * This is <em>not</em> typical either.
     *
     * @param user currently authenticated user
     * @return profile
     */
    @RequestMapping(method=RequestMethod.GET)
    public Profile getProfile(@AuthenticationPrincipal UserWithId user) {
        logger.debug("getProfile()");
        // Find and load the profile from the repository
        Profile result = profileRepository.findOne(user.getId());
        // If a profile was not found, create one
        if (result == null) {
            logger.debug("create new profile");
            // Use the same id as the authenticated user as the id for the profile
            Profile profile = new Profile();
            profile.setId(user.getId());
            result = profileRepository.save(profile);
        }
        logger.debug("result={}", result);
        return result;
    }

    /**
     * Save (update) a user profile resource.
     * <p>
     * The security constraints are such that any <em>authenticated</em> user can save <em>their
     * own</em> profile.
     *
     * @param user currently authenticated user
     * @param profile profile to save
     * @return updated profile
     */
    @RequestMapping(method=RequestMethod.PUT)
    @PreAuthorize("#user.getId() == #profile.getId()")
    public Profile saveProfile(@AuthenticationPrincipal UserWithId user, @RequestBody Profile profile) {
        logger.debug("saveProfile(profile={})", profile);
        return profileRepository.save(profile);
    }

}
