package com.marcuslull.aigm.messaging.player.model;

public class PlayerMessage {

    private String message;

    public PlayerMessage() {
    }

    public PlayerMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "PlayerMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}