package com.aero51.moviedatabase.utils;

import java.util.HashMap;
import java.util.List;


public class ActionError {
    private int code;
    private String message;
    private boolean error;
    private HashMap<String, List<String>> errors;

    public ActionError(String message) {
        this.message = message;
    }
}
