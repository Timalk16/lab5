package org.example.commands;

import org.example.exceptions.*;
import org.example.forms.TicketForm;
import org.example.managers.CollectionManager;
import org.example.utility.Shell;

public class UpdateIdCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public UpdateIdCommand(Shell shell, CollectionManager collectionManager) {
        super("update id {element}", "обновить элемент коллекции по ID");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }
    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            var id = Integer.parseInt(args[1]);
            var ticket = collectionManager.getById(id);
            if (ticket == null) throw new NotFoundException();

            shell.println("Введите данные нового билета:");
            shell.ps2();

            var newTicket = (new TicketForm(shell, collectionManager)).build();
            ticket.update(newTicket);

            shell.println("Билет успешно обновлен");
            return true;
        } catch (WrongAmountOfElementsException e) {
            shell.printErr("Выполняется " + getName());
        } catch (CollectionIsEmptyException e) {
            shell.printErr("Коллекция пуста");
        } catch (NumberFormatException e) {
            shell.printErr("Id должно быть числом");
        } catch (NotFoundException e) {
            shell.printErr("Билета с такми ID не существует");
        } catch (ScriptInputException e) {
            e.printStackTrace();
        } catch (FormException e) {
            shell.printErr("Поля билеты не валидны");
        }
        return false;
    }
}
