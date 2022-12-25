package info.theinside.RESTServerApp;

import org.springframework.data.jpa.repository.JpaRepository;

// User table access
public interface UserRepo extends JpaRepository<User, Long> {
    // We'll use this to check the user presence in th DB
    User findByName(String name);
}