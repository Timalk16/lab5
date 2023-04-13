package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.utility.Shell;

public class ShuffleCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public ShuffleCommand(Shell shell, CollectionManager collectionManager) {
        super("shuffle", "перемешать элементы коллекции в случайном порядке");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            shell.println("Выполняется" + getName());
            return false;
        }

        collectionManager.shuffleCollection();
        shell.println("Элементы коллекции перемешаны");
        return true;
    }
}
