package org.example.commands;

import org.example.utility.Shell;

public class ExecuteScriptCommand extends Command {
    private Shell shell;
    public ExecuteScriptCommand(Shell shell) {
        super("execute_script", "исполнить скрипт из файла");
        this.shell = shell;
    }

    @Override
    public boolean execute(String[] args) {
        if (args[1].isEmpty()) {
            shell.println("Выполняется " + getName());
            return false;
        }

        shell.println("Выполняется скрипт " + args[1]);
        return true;
    }
}
