package com.geektrust.backend.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.geektrust.backend.exceptions.NoSuchCommandException;

public class CommandInvoker {
    private static final Map<String, ICommand> commandMap = new HashMap<>();

    public void register(String commandName, ICommand command) {
        commandMap.put(commandName,command);
    }

    private ICommand get(String commandName) {
        return commandMap.get(commandName);
    }

    public void executeCommand(String commandName, List<String> tokens) {
        ICommand command = get(commandName);
        if(command == null) {
            throw new NoSuchCommandException();
        }
        command.execute(tokens);
    }
}
