package org.example.managers;

import org.example.commands.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private Map<String, Command> commands = new HashMap<>();
    private List<String> commandHistory = new ArrayList<>();

    public void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }

    public List<String> getCommandHistory() {
        return commandHistory;
    }

    public void addCommandToHistory(String command) {
        commandHistory.add(command);
    }
}
