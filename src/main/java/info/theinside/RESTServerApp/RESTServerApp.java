// This is a REST app that has two POST endpoints and H2 database with two tables
// You can log in as a default user and send a message to the server to make it store it in DB
// Port: 3000
// Template for the project https://spring.io/guides/gs/rest-service/
// To check H2 db http://localhost:3000/h2-console/
// To implement JWT https://github.com/auth0/java-jwt

package info.theinside.RESTServerApp;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

// Spring configuration initialization
// It'll scan all dependencies and let us use autowired
@SpringBootApplication
// RESTful's methods to handle incoming HTTP requests
@RestController
public class RESTServerApp {

    // Injecting a pass to DB
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;

    // Start the app with run method with args
    public static void main(String[] args) {
        SpringApplication.run(RESTServerApp.class, args);
    }

    // Run after app started
    @PostConstruct
    public void init() {
        // Add the init user into user table
        userRepo.save(new User("admin", "qwerty"));
    }

    // POST requests handling to the "/messenger" endpoint
    @PostMapping("/messenger")
    public void getMessage(@RequestBody MessageResponse messageResponse) {
        // Find user in DB
        User user = userRepo.findByName(messageResponse.getName());
        // Save message in DB
        messageRepo.save(new Message(user, messageResponse.getMessage()));
    }

    // POST requests handling to the "/token" endpoint
    // Checks login and password if ok return token
    @PostMapping("/token")

    // TokenResponse is instance holds token in string to be returned
    // CreateTokenArgs is instance from request body (reads json)
    // Exceptions to send error codes as response
    public ResponseEntity<TokenResponse> createToken(@RequestBody CreateTokenArgs args) throws Exception {
        System.out.println("name is " + args.name);
        System.out.println("password is " + args.password);
        User user = userRepo.findByName(args.name);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("Got it");
            if (args.password.equals(user.getPassword())) {
                System.out.println("password match");
                // We need a method to create a token as a string to put it here
                return ResponseEntity.ok(new TokenResponse("Here is a token"));
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}