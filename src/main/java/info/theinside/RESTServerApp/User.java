package info.theinside.RESTServerApp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

// JPA entity to represent the table
@Entity(name = "users")
public class User {
    // To get primary keys (foreign keys as a result)
    private @Id
    // To automatically generate keys
    @GeneratedValue Long id;
    private String name;
    private String password;
    // One user can have many messages
    @OneToMany(mappedBy = "user")
    // Getting several messages from the user
    private List<Message> messages;

    public User() {
    }
    // For init user
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
