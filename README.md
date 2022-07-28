**[Micronaut](https://micronaut.io) Java library to consume the [Gitter REST API](https://developer.gitter.im/docs/rest-api)**

## Gitter API Micronaut Java Library

- [Code Repository](https://github.com/softamo/gitter-rest-api)
- [Releases](https://github.com/softamo/gitter-rest-api/releases)

This project is a Java libray to consume the[Gitter REST API](https://developer.gitter.im/docs/rest-api). 
It is built with the [Micronaut](https://micronaut.io) Framework and you can use it in a Micronaut app or as a standalone library.

## Dependency snippet

To use it with https://gradle.org[Gradle]:

`implementation 'com.softamo:gitter-rest-api:XXXX'`

To use it with https://maven.apache.org[Maven]:

```xml
<dependency>
    <groupId>com.softamo</groupId>
    <artifactId>gitter-rest-api</artifactId>
    <version>xxx</version>
    <type>pom</type>
</dependency>
```

## Usage

If you want to use the library in Micronaut application, the library registers in the Micronaut's application context beans of type `como.softamo.gitter.restapi.GitterApi` and `como.softamo.gitter.restapi.BlockingGitterApi` if you have a bean of type `GitterConfiguration`. Providing configuration for your gitter token, registers a bean of type `GitterConfiguration. 

```yaml
gitter:
  token: 'xxx'
```

You can use the library without a Micronaut Application Context. In that case, instantiate `DefaultBlockingGitterApi` or `DefaultGitterApi`.

```java
BlockingGitterApi gitterApi = new DefaultBlockingGitterApi(() -> "xxxx", new ManualBlockingGitterClient());
```
or
```java
GitterApi gitterApi = new DefaultGitterApi(() -> "xxxx", new ManualGitterClient());
```

It is easy to send messages:
```
gitterApi.sendMessage(roomId, "Hello World");
```

You can obtain the room id by getting a list of rooms. 

```java
gitterApi.findAllRooms();
```

## Build

This library uses https://gradle.org[Gradle].

It uses the plugins:

- [Gradle Build Scan Plugins](https://plugins.gradle.org/plugin/com.gradle.build-scan)

## Release instructions

### snapshot:

- Make sure version ends with `-SNAPSHOT`
- `./gradlew publish`

### release:

- bump up version
- Tag it. E.g. v1.0.0
- `./gradlew publishToSonatype closeSonatypeStagingRepository`

Go to `https://s01.oss.sonatype.org/#stagingRepositories` and release repository.