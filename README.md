# falcon
Falcon test project
Installation
1. Create falcon_db using resources\sql\create_db.sql (used MySQL)
2. Configure in startFalcon.bat DB URI, User and Password
3. Build project using mvn clean install
4. run startFalcon.bat

Server starts on 8081
Provides 2 web services :

GET localhost:8081/message/ID - retrieves message in Json format or return error

POST localhost:8081/message - with body data
{
    "messageID": 123,
    "messageText": "Some message"
}
stores or update a message

Provides websocket service :

ws://localhost:8081/socket
Listening channel for receiving/updating new message
in resources\static is places index.html - a demo websocket client, which is the default application page too.
