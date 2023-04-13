package org.example.commands;

import org.example.exceptions.FormException;
import org.example.exceptions.ScriptInputException;
import org.example.exceptions.WrongAmountOfElementsException;
import org.example.forms.TicketForm;
import org.example.managers.CollectionManager;
import org.example.models.Ticket;
import org.example.utility.Shell;

public class AddIfMinCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public AddIfMinCommand(Shell shell, CollectionManager collectionManager) {
        super("add_if_min {element}", "добавить новый элемент, если его цена меньше минимальной цены этой коллекции");
        this.shell = shell;
        this.collectionManager = collectionManager;

    }


    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new WrongAmountOfElementsException();
            shell.println("Создание нового билета (add_if_min)");
            Ticket ticket = new TicketForm(shell, collectionManager).build();

            var minPrice = minPrice();
            if (ticket.getPrice() < minPrice) {
                collectionManager.addElement(ticket);
                shell.println("Билет добавлен");
            } else {
                shell.println("Билет не добавлен, цена не минимальна " + ticket.getPrice());
            }
            return true;
        } catch (WrongAmountOfElementsException e) {
            shell.printErr("Неправильное кол-во аргументов");
            shell.println("Выполняется " + getName());
        } catch (FormException e) {
            shell.printErr("Поля билета не валидны, билет не создан");
        } catch (ScriptInputException e) {}
        return false;
    }

    private Double minPrice() {
        return collectionManager.getCollection().stream()
                .map(Ticket::getPrice)
                .mapToDouble(Double::doubleValue)
                .min()
                .orElse(Long.MAX_VALUE);
    }
}
