## The-Inside assignment
Here is a RESTful HTTP server on Spring with two POST endpoints and H2 in memory database where you can log in and send messages into

### How to test
You can download the project
```
$ git clone https://github.com/Arirokk/rest-assignment
```
Then start it with Gradle in Terminal or in your IDE
#### Curl requests:
I suggest testing with curl in Terminal  
Port: 3000  
The endpoint for log in: "/token"  
The endpoint for messaging: "/messenger"  
To test user authorization:  

Correct (return token in terminal)
```
curl http://localhost:3000/token -H 'Content-Type: application/json' -d '{"name":"admin","password":"qwerty"}'
```
Wrong username (404)
```
curl http://localhost:3000/token -H 'Content-Type: application/json' -d '{"name":"dmin","password":"qwerty"}'
```
Wrong password (401)
```
curl http://localhost:3000/token -H 'Content-Type: application/json' -d '{"name":"admin","password":"12345678"}'
```
Wrong username and password (404)
```
curl http://localhost:3000/token -H 'Content-Type: application/json' -d '{"name":"torvalds","password":"armisok"}'
```
To test message sending (to check new message presence http://localhost:3000/h2-console/)
```
curl http://localhost:3000/messenger -H 'Content-Type: application/json' -d '{"name":"admin","message":"Hello, World!"}'
```
To do so with .json files (samples are in the project folder):
```
curl http://localhost:3000/token -H 'Content-Type: application/json' -d @filename
```