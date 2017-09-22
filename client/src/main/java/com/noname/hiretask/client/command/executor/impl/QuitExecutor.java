package com.noname.hiretask.client.command.executor.impl;

import com.noname.hiretask.client.command.executor.CommandExecutor;
import com.noname.hiretask.client.util.UserConsoleUtil;
import com.noname.hiretask.common.ProtocolCommand;
import com.noname.hiretask.common.RequestMessage;
import com.noname.hiretask.common.ResponseMessage;

public class QuitExecutor implements CommandExecutor {
    @Override
    public RequestMessage prepareMessageForServer() {
        return new RequestMessage(ProtocolCommand.QUIT, "");
    }

    @Override
    public void onSuccess(ResponseMessage response) {
        UserConsoleUtil.println(response.getBody());
    }
}
