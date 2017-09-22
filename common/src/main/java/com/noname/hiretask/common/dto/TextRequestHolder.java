package com.noname.hiretask.common.dto;

/**
 * Holder of a text request to a server. A string is wrapped into that class for conversion purposes.
 */
public class TextRequestHolder {

    private String request;

    public TextRequestHolder() {
    }

    public TextRequestHolder(String request) {
        this.request = request;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }
}
