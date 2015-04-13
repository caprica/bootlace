Bootlace
========

Bootlace is a fully-functional reference application demonstrating the integration of various
contemporary technologies that comprise a modern web development stack.

The purpose of Bootlace is to demonstrate how to integrate, in a robust manner, at least these
technologies together in a single-page web application:

 - AngularJS;
 - Restangular;
 - Bootstrap;
 - Spring MVC;
 - Spring Security;
 - Spring Data;
 - MongoDB;
 - WebSockets;
 - Jetty.

A primary goal of the project is to have a fully working single page web application with
reliable authentication and authorisation implemented using AngularJS on the front-end and Spring
Security the middle-tier.

The provided code and other resources are all intended to be clean, well structured and documented
to a reasonable degree rather than being just a splat of seemingly random code dumped in a
repository.

It is also very important to demonstrate how to structure the project Javascript code, specifically
being organised into modules using "require".

A further important goal of the project is to make the development workflow as convenient as
possible.


AngularJS 1.x vs AngularJS 2.x
------------------------------

This project is currently implemented around AngularJS 1.x, since AngularJS 2.0 is still some time
away - since AngularJS 2.x is the future, a goal of this project is to stay current and apply the
changes necessary to move to AngularJS 2.x in the future (but not yet!). 


Jetty vs Tomcat
---------------

Jetty is a modern implementation of a slick, lean servlet container. It has also has leading edge
support for various modern technologies.

By default Spring Boot uses Tomcat so using Jetty requires extra configuration. It would be quite
simple to drop Jetty for Tomcat, but Jetty is preferred.


Additional Components
---------------------

For convenience, some other popular and useful technologies are also integrated.

Additional front-end libraries include:

 * angular-toastr;
 * bootswatch;
 * blueimp-gallery;
 * font-awesome;
 * moment.js

Some of these libraries are chosen because they require special handling when integrating (e.g.
Browserify shims).

Additional middle-tier libraries include:

 * BCrypt;
 * Google Guava;
 * OWASP Java HTML Sanitizer

Additional front-end build tools include:

 * jshint;
 * npm;
 * Browserify;
 * Stylus.

Additional project build tools include:

 * Cobertura;
 * FindBugs;
 * JUnit;
 * PMD.

Bootlace uses as much as possible the latest versions of the various libraries and frameworks.


Project Status
--------------

This project is a work-in-progress.

What has been provided works, and demonstrate a working implementation of many of the project
goals.

However, there is still more to be done. 

The intent is to create a canonical implementation to serve as fully working reference material
when developing new projects using the same (or very similar) software stack.

Discussion, contributions, patches and pull-requests are all welcome.


Prerequisites
-------------

The following is the minimum list of prerequisites:

 * You must have a MongoDB database installed.
 * You must have Java 1.8 installed.
 * You must have Maven 3.x installed.
 * You must have Node.js installed, at least for "npm".

As is the case with a lot of modern web development, a Linux or Linux-like environment is
preferred - this is especially the case for the Javascript and CSS development tools and anything
else related to NodeJS (like the "npm" tool). 


Maven
-----

Maven is used to build the project.

Since the project is a Spring Boot project, Maven can also be used to start the application in a
local HTTP server using standard Spring Boot goals.

The project Maven pom.xml is configured to generate "site" documentation, including Javadoc, test
and static analysis results.


Rebuilding Assets
-----------------

The Javascript and CSS assets are structured as many separate files in various modules. Part of the
project build process requires that those separate files be combined into a single file. This can
become pretty tedious if it becomes necessary to drop to the command-line and execute shell
commands every time a file is changed.

Consequently, the "npm" tools is used to automate this.

Open a terminal window, and once in the project directory only one command is needed: 

> npm run watch

This starts a process that monitors (separately) Javascript and CSS files for changes and rebuilds
them accordingly.

If the server is running, the new files should be automatically deployed and start being used
immediately (perhaps requiring a browser refresh, unless browser caching is disabled e.g. with
Chrome Developer Tools).


Eclipse
-------

A goal of this project is to provide for a convenient development workflow, as part of that when
using Eclipse it is recommended that automatic workspace refreshes are enabled.

This *can* have a detrimental performance impact, but since project assets like CSS files and
Javascript files are processed (combined into a single file and minified) outside of Eclipse then
automatic workspace refreshes are highly desirable.

To enable this, in Eclipse:

> Window -> Preferences -> General -> Workspace -> Refresh using native hooks or polling


Running the Application
-----------------------

You simply need to execute the maven "spring-boot:run" goal, either on the command-line or via an
Eclipse "Run" configuration.

When the application is started, point a web browser at:

> http://localhost:8080

The home page should appear.

During application startup a default "admin" user and a non-admin "user" are created in the
database - the passwords are "admin" and "user" respectively (the same as the username). You can
use these user accounts to login to the application.


Browser Support
---------------

The supported environments for this project are the the so-called "evergreen" browsers, primarily
Chrome and Safari, followed by Firefox.

Other browsers should work if they are compliant with modern standards.


License and Copyright
---------------------

Bootlace is distributed according to the terms of the
[Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt "Apache License 2.0")

Copyright (C) 2015 [Caprica Software Limited](http://capricasoftware.co.uk)

If this project was useful to you, we kindly ask that you give some credit back to us with a link
somewhere in your project documentation or credits.


Credits
-------

This project was based _in part_ on material published as below:

 * [Techniques for Authentication in AngularJS Applications](https://medium.com/opinionated-angularjs/techniques-for-authentication-in-angularjs-applications-7bbf0346acec)
 * [spring-security-angular](https://github.com/dsyer/spring-security-angular)
 * [AngularJS with Browserify](http://benclinkinbeard.com/talks/2014/ng-conf)
 * [Angular Style Guide](https://github.com/johnpapa/angular-styleguide)
 * [How to Use npm as a Build Tool](http://blog.keithcirkel.co.uk/how-to-use-npm-as-a-build-tool)

Pulling everything together was not easy, most material out there explains only 80% of what is
needed and eschews many tricksy but vital things.


Other Recommended Links
-----------------------

 * [Ben Clinkinbeard](http://benclinkinbeard.com) - for general articles on AngularJS and related things


TODO (Application)
------------------

 * WebSockets integration
 * Form validation errors on client
 * Server side validation for forms, e.g. use of @Valid and BindingResult
 * Full set of responsive styles
 * Check use of replace() when changing route state, e.g. in the error cases it is not quite right in all cases
 * Create account (e.g. with an email validation workflow)
 * Delete account
 * Custom Spring Data repository methods, also custom MongoDB queries using the builder pattern
 * File upload
 * Javascript image gallery
 * Javascript unit tests
 * Java unit tests
 * Launch npm watch process via maven command?
 * Custom methods in MongoDB repository;
 * Full editing of an example resource, CRUD, showing normal usage of Restangular;


TODO (Technical)
----------------

 * Minification of assets, cache busting filename hashes, and applying an asset map;
 * Integrate live-reload for browsers?
 * Use authenticated username and password for MongoDB database connection;
 * Build and deploy to OpenShift, or Heroku or something.
 * Custom secure the management endpoints, see https://github.com/spring-projects/spring-boot/blob/master/spring-boot-docs/src/main/asciidoc/production-ready-features.adoc
