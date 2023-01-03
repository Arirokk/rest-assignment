// This is a REST app that has two POST endpoints and H2 database with two tables
// You can log in as a default user and send a message to the server to make it store it in DB
// Port: 3000
// Template for the project https://spring.io/guides/gs/rest-service/
// To check H2 db http://localhost:3000/h2-console/
// To implement JWT https://github.com/auth0/java-jwt

package info.theinside.RESTServerApp;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.security.Key;

// Spring configuration initialization
// It'll scan all dependencies and let us use Autowired
@SpringBootApplication
// turns on RESTful's methods to handle incoming HTTP requests
@RestController
public class RESTServerApp {
    // We'll store it here to prevent changing during one server session
    private Key secret;
    // Injecting passes to DBs
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MessageRepo messageRepo;

    // Start the app with run method with args
    public static void main(String[] args) {
        SpringApplication.run(RESTServerApp.class, args);
    }

    // Run right after the app started
    @PostConstruct
    public void init() {
        // Add the init user into user table
        userRepo.save(new User("admin", "qwerty"));
    }
    @PostConstruct
    private Key generateSecretKey() {
        secret = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        return secret;
    }

    // POST requests handling
    @PostMapping("/messenger")
    // Method gets a request body as a parameter that uses Message
    // todo this EP gets name+message, but checks bearer from header
    public void postMessage(@RequestBody MessageContent args) throws Exception {
        // Find user in DB
        User user = userRepo.findByName(args.name);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            // Save message in DB
            messageRepo.save(new Message(user, args.message));
        }
    }

    // Checks login and password, if ok, returns a token
    @PostMapping("/token")
    // ResponseEntity will be sent from the endpoint
    // TokenResponse is instance that holds token in string to be returned
    // CreateTokenArgs is instance from request body (reads json)
    // Exceptions to send error codes as response
    public ResponseEntity<TokenResponse> createToken(@RequestBody CreateTokenArgs args) throws Exception {
        // To store token (maybe it's better to move to scope above)
        String jws;
        // Check if RequestBody was caught
        System.out.println("name is " + args.name);
        System.out.println("password is " + args.password);
        // Find user with the name
        User user = userRepo.findByName(args.name);
        if (user == null) {
            // HttpStatus.java to choose the code
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        } else {
            System.out.println("----name match");
            if (args.password.equals(user.getPassword())) {
                System.out.println("----password match");
                // We need a method to create a token as a string to put it here
                jws = Jwts.builder()
                        .claim("name", args.name)
                        .signWith(secret)
                        .compact();
                return ResponseEntity.ok(new TokenResponse(jws));
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }
    }
}