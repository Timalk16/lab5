package org.example.commands;

import org.example.managers.CommandManager;
import org.example.utility.Shell;

public class HelpCommand extends Command {
    private Shell shell;
    private CommandManager commandManager;

    public HelpCommand(Shell shell, CommandManager commandManager) {

        super("help", "информация о всех командах");
        this.shell = shell;
        this.commandManager = commandManager;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            shell.println("Выполняется " + getName());
            return false;
        }

        commandManager.getCommands().values().forEach(command -> {
            shell.printTable(command.getName(), command.getDescription());
        });
        return true;
    }
}
