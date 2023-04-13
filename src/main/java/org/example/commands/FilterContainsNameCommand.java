package org.example.commands;

import org.example.exceptions.WrongAmountOfElementsException;
import org.example.managers.CollectionManager;
import org.example.models.Ticket;
import org.example.utility.Shell;

import java.util.List;
import java.util.stream.Collectors;

public class FilterContainsNameCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public FilterContainsNameCommand(Shell shell, CollectionManager collectionManager) {
        super("filter_contains_name NAME", "вывести элементы, значения поля name содержит заданную подстроку");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }



    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new WrongAmountOfElementsException();
            var tickets = filterByName(args[1]);

            if (tickets.isEmpty()) {
                shell.println("Билеты с именем " + args[1] + " не обнаружены");
            } else {
                shell.println("Билеты с именем " + args[1] + " обнаружены");
                tickets.forEach(shell::println);
            }
            return true;
        } catch (WrongAmountOfElementsException e) {
            shell.printErr("Неправильное кол-во аргументов");
            shell.printErr("Выполняется: " + getName());
        }
        return false;
    }

    private List<Ticket> filterByName(String nameSubstring) {
        return collectionManager.getCollection().stream()
                .filter(ticket -> ticket.getName() != null && ticket.getName().contains(nameSubstring))
                .collect(Collectors.toList());
    }
}
