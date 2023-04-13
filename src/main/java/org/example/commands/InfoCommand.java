package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.utility.Shell;

import java.time.LocalDateTime;

public class InfoCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public InfoCommand(Shell shell, CollectionManager collectionManager) {
        super("info", "вывести информацию о коллекции");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            shell.println("Выполняется" + getName());
            return false;
        }

        LocalDateTime lastInitTime = collectionManager.getLastInitTime();


        shell.println("Сведения о коллекции: ");
        shell.println("Тип коллекции: " + collectionManager.collectionType());
        shell.println("Кол-во элементов коллекции: " + collectionManager.collectionSize());
        shell.println("Дата инициализации: " + lastInitTime);
        return true;
    }
}
