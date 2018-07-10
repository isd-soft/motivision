# MotiVision

Here you will find an implementation of Restful API App (Server) & libGdx Android (Client)
This project is made to encourage practicing sports such as volleyball, swimming, push ups, pull ups and so on.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment notes from below on how to deploy the project on a live system.

### Steps to Setup the Server

1. Clone the software

```
git clone http://github.com/isd-soft/motivision
```

2. Create PostgreSQL database

```
create database motivision;
```

3. Change PostgreSQL user and password:

```
./api/src/main/resources/application.properties
spring.datasource.username = [your_username]
spring.datasource.password = [your_password]
```

4. Restore the database from Dump

```
./motivision/dump.sql
psql --username=[your_username] motivision < dump.sql
Example:
psql --username=postgres motivision < dump.sql
```

5. Specify Connection Addres for Database

```
./api/src/main/resources/application.properties
spring.datasource.url=jdbc:postgresql://[your_ip/localhost/server_ip]:[your_port]/motivision

Example for running it on local machine:
spring.datasource.url=jdbc:postgresql://localhost:5432/motivision
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management (Server)
* [Gradle](https://gradle.org/) - Build tool (Client)

## Deployment (Server)

Deployment can be done on Windows/Unix Based Machine with JDK 8 and above

To run the Server just write:

```
java -jar ./motivision/api/target/motivision-rest-service-1.0.0.jar
```

If there is no jar file then just build it and try again:
```
cd ./motivision/api/
mvn package
```

## Done!