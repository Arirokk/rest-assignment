package info.theinside.RESTServerApp;

import jakarta.persistence.*;

// JPA entity to represent the table
@Entity(name = "messages")

public class Message {
    // To get primary keys
    private @Id
    // To automatically generate keys
    @GeneratedValue Long id;
    // We instruct that one user can hve many messages
    @ManyToOne
    // Pointing the relation out
    @JoinColumn(name = "user.id")
    private User user;
    private String message;

    public Message(User user, String message) {
        // Provides a link to user table
        this.user = user;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
