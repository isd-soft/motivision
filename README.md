# MotiVision

This project is made to encourage practicing sports such as volleyball, swimming, push ups, pull ups and so on.
Here you will find an implementation of Restful API (Server) & libGdx Android (Client) App.
<p align="center">
![MotiVision](readme/MotiVision.gif)
</p>
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
 
If you have already "motivision" database then run:

```
DROP DATABASE IF EXISTS motivision;
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
 
 If you want to run server as a .jar file from terminal just write:
 ```
 cd /motivision/api/
 
 Linux/Mac OS:
  sh mvnw.sh
 
 Windows:
  mvnw.cmd
 ```


To run it as a .war read following:
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

-------------------------------------------------------------------------
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

## List of existing controllers:
```
Team
Login
Character
Activities
Battle
Login
Test
Item
```

Where you will find /get_XXX - then it's a GET method request
Request Method in "Test,Item" Controllers is GET
Where you will find /delete_XXX - then it's a DELETE method request
Other requests has POST method


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
```

/create_team?teamName=X&teamLogo=X&battleFrequency=X
```
   /*
    *  Create team request
    *  Used to create new team and add default 6 activities to it
    *  @param name - team name
    *  @param logo - team logo
    *  @param battleFrequency - team battleFrequency
    *  @return status failed if team name already exists
    *  @return status failed if somehow default activities are not present
    *  @return status success if team was successfully created
    * */
```
/delete_team?teamId=X
```
   /*
    * Delete team request
    * Used to delete a team by teamId
    * @param teamId - team for deletion
    * @return status failed if no such team exists
    * @return status success if team was deleted successfully
    * */
```

/update_team?teamId=X&teamLogo=X&battleFrequency=X&lock=X
```
    /*
     * Update team request
     * Used to update a team by teamId
     * @param teamId - team for updating
     * @param teamLogo - new team logo
     * @param battleFrequency - new battle frequency
     * @param lock - lock or unlock a team
     * @return status failed if no such team exists
     * @return status success if team was deleted successfully
     * */
```
/team_exist?teamName=X
```
    /*
     * Team exist request
     * Used to check if a team exists by teamName
     * @param teamName - team name for deletion
     * @return status failed if no such team exists
     * @return status succes with teamId and with lock status if team is locked
     * */
```

### Login(Player) has following requests:

/login?login=X&password=X
```
    /*
     * Login controller
     * Used to login to database with a valid username and password
     * @param login - user login
     * @param password - user password
     * @return Json data
     * */
```

/register_player?login=X&password=X
```
    /*
     * Register player controller
     * Used to register a new player in the database
     * with a unique name
     * @param login - user login to register
     * @param password - user password to register
     * @return status - failed if no data was provided
     * @return status - success and playerId if player was registered
     * */
```

/player_exist?login=X
```
    /*
     * Check if player exist controller
     * Used to check if a player exist in the database
     * @param login - login to check in the database
     * @return message false if player doesn't exist
     * @return message true if player does exist
     * */
```

### Character(Profile) has following requests:

/create_character?playerId=X&teamId=X&headType=X&bodyType=X&gender=X&characterName=X&isAdmin=X
```
    /*
     * Create character request
     * @param playerId - player for which to create character
     * @param teamId - team for the character to be in
     * @param headType - character headType
     * @param bodyType - character bodyType
     * @param gender - character gender
     * @param characterName - character name
     * @param isAdmin - map character as team leader in teamId team
     * @return Json data with status success and newly created characterId
     * @return if everything went well and status failed and message if something
     * @return went wrong
     * */
```

/get_character?characterId=X
```
    /*
     * Get character request
     * @param characterId - Id of the required character
     * @return Json data containing status being failed if no
     * @return such character was found in database and being success
     * @return if  character was found
     * @return All info on character:
     * @return characterId, characterName, playerId, teamId,
     * @return isAdmin, headType, bodyType, gender, items
     * @return items - null if character has no items
     * @return items - list where each element contains item
     * @return data if character has items
     * */
```

/delete_character?characterId=X
```
    /*
     * Delete character request
     * @param characterId - Id of the character for deletion
     * @return status - failed, message - no such character exist if
     * @return no character with characterId was found and
     * @return status - success if character with characterId was successfully deleted
     * */
```

/character_exist?characterName=X
```
    /*
     * Character exist request
     * @param characterName - name of the character for database search,
     * @return status - success, message - true if character exists
     * @return status - success, message - false if character doesn't exists
     * */
```

/get_items?characterId=X
```
    /*
     * Get items request
     * @param characterId - character for items search
     * @return status - failed if no such character exist
     * @return status - success items - null if character
     * @return exists but has no items
     * @return Items list if character exists and has items
     * */
```

/delete_item?characterId=X&itemId=X
```
    /*
     * Delete item request
     * @param characterId - character to delete an item from
     * @param itemId - item for deletion
     * @return status - failed if no such character exists
     * @return status - failed if the character already doesn't have the Item
     * @return status - success if the item was deleted successfully
     * */
```

/buy_item?characterId=X&itemId=X
```
    /*
     * Buy item request
     * @param charId - character that buys the item
     * @param itemId - Item to be bought
     * @return status - failed if no such character exist
     * @return status - failed if no such item exist
     * @return if the item was bought then status success and
     * @return character pays points equivalent to item price
     * */
```

/unequip_item?characterId=X&itemId=X
```
    /*
     * Unequip item request
     * @param characterId - character that unequips the item
     * @param itemId - Item to be unequiped
     * @return status - failed if no such character exist
     * @return status - failed if no such item exist
     * */
```

/equip_item?characterId=X&itemId=X
```
    /*
     * Equip item request
     * @param characterId - character that equips the item
     * @param itemId - Item to be equiped
     * @return status - failed if no such character exist
     * @return status - failed if no such item exist
     * */
```

/get_player_characters?playerId=X
```
    /*
     * Get player if such player request
     * @param playerId - player to be found
     * @return status - failed if no such player exist
     * @return status - succes if player found and returns all data
     * */
```

/delete_player?playerId=X
```
    /*
     * Delete player request
     * Used to delete player and all player characters
     * If a character is team admin then the team will be deleted as well
     * and thus all the characters inside that team
     * @param playerId - Player to delete
     * @return status - failed if the player was not found
     * @return status - success if the player was deleted successfully
     * */
```

/get_points=characterId=X
```
    /*
     * Get points request
     * Used to get the points of a character
     * @param characterId - Character to check the amount of points
     * @return status - failed if the character was not found
     * @return status - success if the character has int value of points
     * */
```

### Activities has following requests:

/do_activity?characterId=X&activityId=X
```
    /*
     * Do activity request
     * Used as a points adding system when activity is done
     * @param characterId - character for points adding
     * @param activityId - team activity that the player already did
     * @return status - failed if character not found
     * @return status - failed if activity not found
     * @return status - success if points were assigned successfully
     * */
```

/get_activities?teamId=X
```
    /*
     * Get activity request
     * Used to get a list of all team activities
     * @param teamId - team to search activities in
     * @return status - failed if no such team exist
     * @return status - success if request was successfull
     * @return teamActivities - null if no activities
     * @return teamActivities - list of all activities
     * */
```

/delete_activity?teamId=X&activityId=X
```
    /*
     * Delete activity request
     * @param activityId - Id of the activity for deletion
     * @param teamId - Id of the team where activity will be deleted
     * @return status - failed if no such team with teamId exist
     * @return status - success if activity with activityId was successfully deleted
     * */
```

/create_activity?teamId=X&activityName=X&activityReward=X
```
    /*
     * Create activity request
     * Used to create a new activity for our team
     * @param teamId - team for new activity creation
     * @param activityName - new activity name
     * @param activityReward - new activity reward
     * @return status - failed if no such team exist
     * @return status success if activity was created
     * */
```

/update_activity?teamId=X&activityId=X&activityName=X&activityReward=X
```
    /*
     * Update activity request
     * Used to update a new activity data for our team
     * @param teamId - team for new activity data updating
     * @param activityId - activity to be updated
     * @param activityReward - reward for updated activity
     * @return status - failed if no such team exist
     * @return status success if activity was updated
     * */
```

### Test has following request:

/test
```
    /*
     * Test request
     * Used to ping connection
     * @return status - success if got connection
     * */
```

### Item has following requests:

/get_item?itemId=X
```
    /*
    * Get item request
    * Used to get an item by itemId
    * @param itemId - item to get
    * @return status - failed if the item was not found
    * @return status - success and itemPrice if the item was found
    * */
```

/get_store_items
```
    /*
    * Get item price request
    * Used to get items price for shop
    * @return status - success and items array
    * @return itemId and itemPrice
    * */
```
