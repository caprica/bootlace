'use strict';

/**
 * Google maps configuration.
 *
 * @param uiGmapGoogleMapApiProvider
 */
MapsConfiguration.$inject = ['uiGmapGoogleMapApiProvider'];
function MapsConfiguration(uiGmapGoogleMapApiProvider) {

    uiGmapGoogleMapApiProvider.configure({

        /**
         * Google Maps API key.
         *
         * This key is specific to you/your application.
         *
         * An API key is recommended but maps will work, up to a point, without an API key.
         */
//        key: 'Your API key',

        /**
         * Required version.
         *
         * Specifying e.g. "3" will always pick the latest version in that series.
         *
         * It is possible to specify a specific version, e.g. "3.19", or even the bleeding edge
         * experimental version with "3.exp".
         *
         * If you specify a 'retired' version, then the oldest available version will be used.
         */
        v: '3'

    });
};

module.exports = MapsConfiguration;
