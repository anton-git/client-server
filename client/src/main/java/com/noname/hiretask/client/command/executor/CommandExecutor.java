package com.noname.hiretask.client.command.executor;

import com.noname.hiretask.client.util.UserConsoleUtil;
import com.noname.hiretask.common.RequestMessage;
import com.noname.hiretask.common.ResponseMessage;

/**
 * Classes implementing this interfaces are responsible for executing command on the client.
 * <p> - prepare message for server
 * <p> - deal with server response
 */
public interface CommandExecutor {

    RequestMessage prepareMessageForServer();

    default void acceptServerResponse(final ResponseMessage response) {
        if (ResponseMessage.ResponseCode.OK.equals(response.getCode())) {
            onSuccess(response);
            return;
        }

        onFailure(response);
    }

    default void onSuccess(final ResponseMessage response) {
        UserConsoleUtil.println(getMessageOnServerSuccess());
    }

    default void onFailure(final ResponseMessage response) {
        UserConsoleUtil.println(getMessageOnServerFailure());
        UserConsoleUtil.print("Details: ");
        UserConsoleUtil.println(response.getBody());
    }

    default String getMessageOnServerSuccess() {
        return "SUCCESS";
    };

    default String getMessageOnServerFailure() {
        return "FAILURE";
    };

}
