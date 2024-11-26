package com.acs.Shopping.Cart.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private int statusCode;
    private Object data;
    private String message;
    private boolean success;

    //default success message
    public ApiResponse(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = "Api call Success";
        this.success = statusCode < 400;
    }

    //custom message
    public ApiResponse(int statusCode, Object data, String message) {
        this.statusCode = statusCode;
        this.data = data;
        this.message = message;
        this.success = statusCode < 400;
    }
}

