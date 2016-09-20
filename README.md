# Firebase Chat

A simple mobile chat implemented using Firebase. Project was developed in Android Studio 2. Currently, application supports sign in with Google. Currently, the application is using Heroku cloud platform for server. If you want to setup a local server, please look at [Local server](#local-server) section.

## Screenshots

Sign in screen             |  Chat screen              
:-------------------------:|:-------------------------:|
![Sign in screen](https://github.com/asiljan/FirebaseChat/blob/master/screenshots/sign_in.png?raw=true) | ![Chat screen](https://github.com/asiljan/FirebaseChat/blob/master/screenshots/chat_new_msg.png?raw=true) |

## Installation

#### Android

* Clone or download project as .zip
* Open project in Android Studio
* Change local server address inside your `app/build.gradle` file (otherwise, Push notifications will not work)
* Run

**app/build.gradle**

```gradle

develop {
  initWith debug
  buildConfigField 'String', 'URL_ENDPOINT', '"http://192.168.5.16:3000"'
}

```

#### Local server

In order to receive Push notifications while app is in background you have to follow this steps:

* Install Node.js
* Run the server with `node index.js` command.

Server code is available here: [node server](https://github.com/asiljan/FirebaseChat/tree/master/server)

## Feature improvements

* Add 'copy message' functionality
* Add chat groups...
