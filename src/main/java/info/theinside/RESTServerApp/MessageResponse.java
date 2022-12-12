package info.theinside.RESTServerApp;

// Class to instantiate responses in /messenger endpoint
public class MessageResponse {
    private String name;
    private String message;

    public MessageResponse() {
    }

    public MessageResponse(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}