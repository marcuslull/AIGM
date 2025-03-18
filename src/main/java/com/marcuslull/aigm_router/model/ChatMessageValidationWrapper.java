package com.marcuslull.aigm_router.model;

public class ChatMessageValidationWrapper {

    private boolean isValid = false;
    private String validationProblems;
    private final ChatMessage chatMessage;

    public ChatMessageValidationWrapper(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public String getValidationProblems() {
        return validationProblems;
    }

    public void setValidationProblems(String validationProblems) {
        this.validationProblems = validationProblems;
    }

    public ChatMessage getChatMessage() {
        return chatMessage;
    }
}
