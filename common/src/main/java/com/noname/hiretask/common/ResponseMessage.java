package com.noname.hiretask.common;

/**
 * Response message is sent from a server back to a client.
 */
public class ResponseMessage extends Message{

    private final ResponseCode code;
    private final String body;

    public ResponseMessage(ResponseCode code, String body) {
        this.code = code;
        this.body = body;
    }

    public ResponseCode getCode() {
        return code;
    }

    @Override
    String getHeader() {
        return code.name();
    }

    @Override
    public String getBody() {
        return body;
    }

    public enum ResponseCode {
        OK(1), FAILED(2);

        private int value;

        ResponseCode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }


}
