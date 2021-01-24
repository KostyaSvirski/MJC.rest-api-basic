package com.epam.esm.service.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class CommandManager {

    private TypesOfCommands commands;

    @Autowired
    public CommandManager(TypesOfCommands commands) {
        this.commands = commands;
    }

    public Optional<ActionCommand> defineCommand(String commandToFind) {
        Map<String, ActionCommand> mapOfCommands = commands.getMapOfCommands();
        for (Map.Entry<String, ActionCommand> command : mapOfCommands.entrySet()) {
            if (command.getKey().equals(commandToFind)) {
                return Optional.of(command.getValue());
            }
        }
        return Optional.empty();
    }
}
