package info.theinside.RESTServerApp;

import org.springframework.data.jpa.repository.JpaRepository;

// Message table
public interface MessageRepo extends JpaRepository<Message, Long> {
}