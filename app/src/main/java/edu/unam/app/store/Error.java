package edu.unam.app.store;

public class Error extends Exception {
    private String message;

    public Error(String message) {
        super();
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}