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

## DataBase
To setup the database you first need to install a Postgresql server [link](https://www.postgresql.org/download/). After you've installed it add the bin folder to your  Path system variable.Then access command prompt and change your directory to the folder with the db dump.

 ```
 >cd <path_to_dump>
 ```
          
After you have completed the installation you can login as a postgtres user like so

 ```
 psql postgres -U postgres
 ```
          
Now you can create the database by running the following command

 ```
 CREATE DATABASE motivision;
 ```    
          
After that try running this command

 ```
 >psql -h localhost -p 5432 -U postgres motivision
 ```

And restore the database with 

 ```
 motivision=# \i cleanDump.sql
 ```
          
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
# How to use API

## List of disponible controllers:
```
Team
Player
Character
Activities
Battle
Login
Test
Item
```

### Team has following requests:

/get_team?teamId=X
```
/*
 * Get team info request
 * Used to get team info by teamId
 * @param teamId - id of the team to get data
 * @return if such team exist return Json info about the team
 * @return if no such team exist return Json fail message
 * */
 
 Example of output data:
 
  If team is not found you will see this:
   {"status":"failed","message":"team not found"}
 
  If team is found it returns following JSON data:
  {
     "status": "success",
     "teamId": 1,
     "teamName": "Team",
     "liderId": 24,
     "teamLogo": "noLogo",
     "battleFrequency": 7,
     "teamWins": 172,
     "teamLoss": 30,
     "lock": false,
     "characters": [
         {
             "characterId": 25,
             "characterName": "Admin",
             "playerId": 38,
             "teamId": 17,
             "isAdmin": "true",
             "headType": 1,
             "bodyType": 1,
             "gender": "M",
             "points": 2343
         },
         {
             "characterId": 29,
             "characterName": "User",
             "playerId": 38,
             "teamId": 17,
             "isAdmin": "false",
             "headType": 1,
             "bodyType": 1,
             "gender": "F",
             "points": 300
         }
     ]
 }
 
```

/get_team_members?teamId=X
```
/*
* Get team members request
* Used to get a list of all team members
* @param teamId - team to search characters in
* @return status - failed if no such team exist
* @return status - success if request was successful
* @return teamMembers - null if no teamMembers
* @return teamMembers - list of all teamMembers
* */

 Example of output data:
 
  If team is not found you will see this:
   {"status":"failed","message":"team not found"}
 
  If team is found it returns following JSON data:
  {
      "status": "success",
      "teamMembers": [
          {
              "characterId": "25",
              "characterName": "jijiji",
              "headType": 1,
              "bodyType": 1,
              "gender": "M",
              "points": 0
          },
          {
              "characterId": "29",
              "characterName": "atdasd",
              "headType": 1,
              "bodyType": 1,
              "gender": "M",
              "points": 0
          }
    ]
}
```
--------------------------------------------------------------
# Tomcat
Tutorial (google):
http://www.ntu.edu.sg/home/ehchua/programming/howto/tomcat_howto.html

Tutorial (motivision):

Download: https://tomcat.apache.org/download-80.cgi
![Build you own war](readme/apache/1.png?raw=true "Don't click me")
![Build you own war](readme/apache/2.png?raw=true "Don't click me")
![Build you own war](readme/apache/2.1.png?raw=true "Don't click me")
![Build you own war](readme/apache/3.png?raw=true "Don't click me")
![Build you own war](readme/apache/4.png?raw=true "Don't click me")
![Build you own war](readme/apache/5.png?raw=true "Don't click me")
![Build you own war](readme/apache/6.png?raw=true "Don't click me")
![Build you own war](readme/apache/7.png?raw=true "Don't click me")
![Build you own war](readme/apache/8.png?raw=true "Don't click me")
GO:
![Build you own war](readme/apache/9.png?raw=true "Don't click me")
