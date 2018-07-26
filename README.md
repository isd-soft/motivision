# MotiVision

This project is made to encourage practicing sports such as volleyball, swimming, push ups, pull ups and so on.
Here you will find an implementation of Restful API (Server) & libGdx Android (Client) App.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment notes from below on how to deploy the project on a live system.

# Steps to Setup the Server

Deployment can be done on Windows/Linux/Mac OS with these components installed:

* [JDK 8 and above](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/)
* [PostgreSQL 10](https://www.postgresql.org/download/)

To run the Server just write in cmd/terminal:

```
Linux/Mac OS:
cd motivision/api
sh mvnw.sh

Windows:
cd motivision/api
mvnw.cmd
```

# Steps to Setup the Client

Deployment can be done on Windows/Linux/Mac OS with these components installed:

* [JDK 8 and above](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Android Studio with SDK](https://developer.android.com/studio/)

## Android

You can build apk in Android Studio following instruction:

![Build your own apk](readme/build_apk.png?raw=true "Don't click me")

```
Apk will be saved in following directory:
/motivision/client/android/build/outputs/apk/[your_apk]
```
## Desktop

To build jar in Android Studio just press on Gradle menu from the right corner and then double click on "dist" gradle task:

![Build you own jar](readme/build_jar1.png?raw=true "Don't click me")

```
Executable jar will be saved in following directory:
/motivision/client/desktop/build/libs
```
--------------------------------------------------------------------------------------
# How to use API:
