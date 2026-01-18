package com.vvstepanov.stubservice.dto;

public class UserResponse {
    private String login;
    private String status;

    public UserResponse() {
    }

    public UserResponse(String login, String status) {
        this.login = login;
        this.status = status;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
