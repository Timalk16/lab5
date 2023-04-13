package org.example.commands;

import org.example.managers.CollectionManager;
import org.example.models.Ticket;
import org.example.utility.Shell;

import java.util.Comparator;

public class PrintDescendingPriceCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public PrintDescendingPriceCommand(Shell shell, CollectionManager collectionManager) {
        super("print_descending_price {element}", "вывести значения поля price всех элементов в порядке убывания");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String[] args) {
        if (!args[1].isEmpty()) {
            shell.println("Выполняется" + getName());
            return false;
        }

        shell.print("Цена всех элементов коллекции в порядке убывания");
        collectionManager.getCollection().stream()
                .sorted(Comparator.comparingDouble(Ticket::getPrice).reversed())
                .map(Ticket::getPrice)
                .forEach(System.out::println);
        return true;
    }
}
