package org.example.commands;

import org.example.exceptions.WrongAmountOfElementsException;
import org.example.managers.CollectionManager;
import org.example.models.Ticket;
import org.example.utility.Shell;

import java.util.LinkedHashSet;

public class FilterGreaterDiscountCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public FilterGreaterDiscountCommand(Shell shell, CollectionManager collectionManager) {
        super("filter_greater_than_discount DS", "вывести элемент, значения поля discount которых больше заданного");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }

    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new WrongAmountOfElementsException();

            var discount = Float.parseFloat(args[1]);
            var tickets = filterByDiscount(discount);

            if (tickets.isEmpty()) {
                shell.println("Билеты со скидкой больше чем " + args[1] + " не обнаружены");
            } else {
                shell.println("Билеты со скидкой больше чем " + args[1] + " обнаружены");
                tickets.forEach(shell::println);
            }
            return true;


        } catch (NumberFormatException e) {
            shell.printErr("Скидка должна быть представлена числом");
        } catch (WrongAmountOfElementsException e) {
            shell.printErr("Неправильное кол-во аргументов");
            shell.printErr("Выполняется " + getName());
        }
        return false;
    }

    private LinkedHashSet<Ticket> filterByDiscount(Float discount) {
        LinkedHashSet<Ticket> filtered = new LinkedHashSet<>();
        for (Ticket ticket : this.collectionManager.getCollection()) {
            if (ticket.getDiscount() > discount) {
                filtered.add(ticket);
            }
        }
        return filtered;
    }
}
