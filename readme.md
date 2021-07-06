Activity recommendation service
===============================

Provides activity recommendations for users.

## Requirements

Be sure to have the followings installed:

- JDK 11
- Maven 3.6.1+ (or use provided `mvnw`)

## Development

The application is a standard Spring boot 2.x application thus can be started accordingly.

The main application class is `com.funwithactivity.recommendationservice.RecommendationServiceApplication`.

The default spring profile is using real endpoints.

The `ci` spring profile delegates downstream calls to `localhost:${mock.server.port}` where a compatible mock service should be running.

### Acceptance tests

The application functionality is covered with acceptance tests in the `acceptance-test` module. Tests in this module are running against a started
application with `ci` spring profile against an in-process `mock-server`.
See `com.funwithactivity.recommendationservice.acceptancetest.activity.ActivityProviderFunctionalTest` for example.
See `com.funwithactivity.recommendationservice.acceptancetest.boot.IntegrationTestBootstrap` for more details.

There are two hardcoded users present for testing purposes:
- user A:
  - username: `user`
  - password: `pass`
- user B:
  - username: `user2`
  - password: `pass`
  
See `com.funwithactivity.recommendationservice.web.configuration.WebSecurityConfiguration.userDetailsService` for details.

## API
The api documentation can be reached under `/api-docs`.
