const firebase = require('firebase');
const request = require('request');
const express = require('express');
const bodyParser = require('body-parser')

const API_KEY = 'AIzaSyBRSl97tgtYoQiWGGKUxOiPhUygKnBtX5c';

const config = {
  apiKey: 'AIzaSyA1CHTck9pF7eSeR0-9283IKZItZgH8hk4',
  authDomain: 'friendlychat-41c04.firebaseapp.com',
  databaseURL: 'https://friendlychat-41c04.firebaseio.com',
  storageBucket: 'friendlychat-41c04.appspot.com',
};

firebase.initializeApp(config);
const ref = firebase.database().ref();

function listenForNotificationRequests() {
  const requests = ref.child('notificationRequests');
  requests.on('child_added', (requestSnapshot) => {
    const request = requestSnapshot.val();
    sendNotificationToUser(
      request.username,
      request.title,
      request.message,
      () => {
        requestSnapshot.ref.remove();
      }
    );
  }, (error) => {
    console.error(error);
  });
}

function sendNotificationToUser(username, title, message, onSuccess) {
  console.log(`User ${username} created the message "${message}"`);
  Object.keys(users).forEach(user => {
    if (user !== username) {
      console.log(`Sending notification to user ${user}`);
      request({
        url: 'https://fcm.googleapis.com/fcm/send',
        method: 'POST',
        headers: {
          'Content-Type' : 'application/json',
          'Authorization': `key=${API_KEY}`
        },
        body: JSON.stringify({
          notification: {
            title: title,
            body: message
          },
          to: users[user],
          priority: 'high',
          content_available: true
        })
      }, (error, response, body) => {
        console.log(body);
        if (error || (response.body && response.body.error)) {
          console.error(error);
        } else if (response.statusCode >= 400) {
          console.error(`HTTP Error: ${response.statusCode} - ${response.statusMessage}`);
        } else {
          onSuccess();
        }
      });
    }
  });
}

// start listening
listenForNotificationRequests();

const PORT = 3000;
const app = express();

const users = {};

app.use(bodyParser.json());

app.post('/token', (req, res) => {
  if (!req.body || !req.body.user || !req.body.token) {
    return res.sendStatus(400);
  }
  users[req.body.user] = req.body.token;
  console.log(users);
  res.send({});
});

app.listen(PORT, () => {
  console.log(`Listening on port ${PORT}`);
});
