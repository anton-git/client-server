package com.noname.hiretask.client.util;

import com.noname.hiretask.common.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Class contains a static method for extracting a message from the server response.
 */
public class ResponseMessageExtractor {

    private static Logger log = LoggerFactory.getLogger(ResponseMessageExtractor.class);

    /**
     * Gets a {@link ResponseMessage} from a {@link BufferedReader}
     *
     * @param br {@link BufferedReader} to get message from
     * @return {@link ResponseMessage} with extracted message
     * @throws IOException If an I/O error occurs while reading from BufferedReader
     */
    public static ResponseMessage extract(final BufferedReader br) throws IOException {
        final String responseStatus = br.readLine();
        log.debug("RESPONSE status: {}", responseStatus);
        ResponseMessage.ResponseCode responseCode = ResponseMessage.ResponseCode.valueOf(responseStatus);

        final StringBuilder stringBuilder = getResponseBody(br);
        final String responseBody = stringBuilder.toString();
        log.debug("RESPONSE body  : {}", responseBody);
        return new ResponseMessage(responseCode, responseBody);
    }

    private static StringBuilder getResponseBody(BufferedReader br) throws IOException {
        final StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (true) {
            line = br.readLine();
            log.debug("Line: " + line);
            if ("EOL".equals(line)) {
                break;
            }
            stringBuilder.append(line).append("\n");

        }
        return stringBuilder;
    }
}
