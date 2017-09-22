package com.noname.hiretask.server.executor;

import com.noname.hiretask.common.ProtocolCommand;
import com.noname.hiretask.server.executor.impl.*;

import java.util.Optional;

/**
 * Executor factory. Using {@link ExecutorFactory#getExecutor(ProtocolCommand)} method you can get the executor
 * responsible for command execution.
 */
public class ExecutorFactory {

    /**
     * Gets the {@link Executor} responsible for passed command execution
     *
     * @param command command requested by a client to execute
     * @return Corresponding {@link Executor} or {@link IllegalArgumentException} of none was found.
     */
    public static Executor getExecutor(final ProtocolCommand command) {
        final Optional<Executor> executor = Optional.ofNullable(findExecutor(command));
        return executor
                .orElseThrow(() -> new IllegalArgumentException("No executors correspond to protocol command"));
    }

    private static Executor findExecutor(final ProtocolCommand command) {
        switch (command) {
            case ADD_BIRD: {
                return new AddBirdExecutor();
            }

            case GET_ALL_BIRDS: {
                return new ListBirdsExecutor();
            }

            case ADD_SIGHTING: {
                return new AddSightingExecutor();
            }

            case REMOVE_BIRD: {
                return new RemoveBirdExecutor();
            }

            case GET_SIGHTINGS: {
                return new ListSightingsExecutor();
            }

            case QUIT: {
                return new QuitServerExecutor();
            }
        }

        return null;
    }
}
