package org.example.commands;

import org.example.utility.Shell;

public class ExitCommand extends Command {
    private Shell shell;

    public ExitCommand(Shell shell) {
        super("exit", "выход из программы без сохранения");
        this.shell = shell;
    }
    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            shell.println("Выполняется" + getName());
        }

        shell.println("Выход из программы");
        return true;
    }
}
