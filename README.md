# Demo! Spring Boot Kotlin -- gRPC! for microservices

## Dependencies

- [Spring](https://spring.io/) spring framework
- [gRPC-Spring-Boot-Starter](https://yidongnan.github.io/grpc-spring-boot-starter/en/) gRPC Spring Boot Starter
- [Flywaydb](https://flywaydb.org/) for database migrations

## System Requirements
- Java 11
- Kotlin 1.6.21
- Postgresql

## Initial Setup
```bash
# Install homebrew first
# For Non-Mac user, I'm sure you can find your own way!
$ brew install postgresql
$ brew services start postgresql

# Clone this repository
$ git clone git@github.com:kw-bucket/spring-boot-kotlin--grpc.git

# Migrate schema to latest version
$ mvn -Dflyway.configFiles=flyway.local.conf flyway:migrate
```

## Run Locally
```bash
# Run unit tests
# Code style will be checked after test using ktlint ;-)
$ mvn clean test

# Start application
$ mvn clean spring-boot:run
```

I'm sure and believe that anyone present here will find your way to make better understanding on your own.
Just believe in yourself! 😜
