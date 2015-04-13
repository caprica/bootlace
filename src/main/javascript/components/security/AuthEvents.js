'use strict';

/**
 * Constant definitions for application authentication and authorisation events.
 * 
 * The constants here are used in the various authentication and authorisation components and
 * can also be used in page templates (they are made available via the SessionController).
 */
var AuthEvents = {

    loginSuccess    : 'auth-login-success'    ,
    loginFailed     : 'auth-login-failed'     ,
    logoutSuccess   : 'auth-logout-success'   ,
    notAuthenticated: 'auth-not-authenticated',
    notAuthorised   : 'auth-not-authorised'

};

module.exports = AuthEvents;
