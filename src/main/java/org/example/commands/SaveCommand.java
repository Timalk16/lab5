package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.utility.Shell;

public class SaveCommand extends Command{
    private Shell shell;
    private CollectionManager collectionManager;
    private String fileName;


    public SaveCommand(Shell shell, CollectionManager collectionManager, String fileName) {
        super("save", "сохранить коллекцию в файл");
        this.shell = shell;
        this.collectionManager = collectionManager;
        this.fileName = fileName;
    }
    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            shell.println("Выполняется " + getName());
            return false;
        }

        collectionManager.saveCollection(fileName);
        return true;
    }
}
