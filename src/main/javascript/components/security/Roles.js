'use strict';

/**
 * Roles are constant values used in routing configuration and are made available for use in page
 * templates via the SessionController.
 * 
 * An application is expected to configure its own roles here.
 */
var roles = {

    admin: 'ROLE_ADMIN',
    user : 'ROLE_USER'
        
}

module.exports = roles;
