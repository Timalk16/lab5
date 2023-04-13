package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.utility.Shell;

public class ShowCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public ShowCommand(Shell shell, CollectionManager collectionManager) {
        super("show", "показывает все элементы коллекции");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            shell.println("Выполняется " + getName());
            return false;
        }
        shell.println(collectionManager.getCollection());
        shell.println(collectionManager.getCollection().isEmpty());
        return true;
    }
}
