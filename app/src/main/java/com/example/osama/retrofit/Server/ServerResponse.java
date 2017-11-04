package com.example.osama.retrofit.Server;

/**
 * Created by osama on 11/2/2017.
 */

public class ServerResponse {
    public String success;
    public String message;

    public ServerResponse(String success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
