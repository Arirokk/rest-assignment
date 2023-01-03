## What is it?
Here is a RESTful server on Spring with two POST endpoints and H2 in memory database where you can log in and send messages into.  
/token endpoint checks username and password against the database, and if they match, creates a JWT and return it to the terminal in .json.  
/messenger endpoint checks username, if it finds such a user, writes a message into database.  
The project in development.  
### How to test
Download the project  
```
git clone https://github.com/Arirokk/rest-assignment
```
Then start it with your IDE,  
or start it with Gradle,  
```
./gradlew build
```
```
./gradlew bootRun
```
or you can use [docker image](https://hub.docker.com/repository/docker/arirokk/restserverapp) (H2 console is on)
```
docker pull arirokk/restserverapp
```
Port: 3000  
The endpoint for log in: "/token"  
The endpoint for messaging: "/messenger"  

#### Curl requests:
I suggest testing with curl in Terminal  
Hit /token:  
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
Hit /messenger (to check new message presence http://localhost:3000/h2-console/):  
New message will be written  
```
curl http://localhost:3000/messenger -H 'Content-Type: application/json' -d '{"name":"admin","message":"Hello, World!"}'
```
Wrong username (404)
```
curl http://localhost:3000/messenger -H 'Content-Type: application/json' -d '{"name":"odmin","message":"Hello, World!"}'
```