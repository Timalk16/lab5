package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.utility.Shell;

public class ClearCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public ClearCommand(Shell shell, CollectionManager collectionManager) {
        super("clear", "очистить коллекцию");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            shell.println("Выполняется" + getName());
            return false;
        }

        collectionManager.clearCollection();
        shell.println("коллекция успешно очищена");
        return true;
    }
}
