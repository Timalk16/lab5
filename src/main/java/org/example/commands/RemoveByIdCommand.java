package org.example.commands;

import org.example.exceptions.CollectionIsEmptyException;
import org.example.exceptions.NotFoundException;
import org.example.exceptions.WrongAmountOfElementsException;
import org.example.managers.CollectionManager;
import org.example.utility.Shell;

public class RemoveByIdCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public RemoveByIdCommand(Shell shell, CollectionManager collectionManager) {
        super("remove_by_did ID", "удалить элемент из коллекции по ID");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }


    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new WrongAmountOfElementsException();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmptyException();

            var id = Integer.parseInt(args[1]);
            var ticketToRemove = collectionManager.getById(id);
            if (ticketToRemove == null) throw new NotFoundException();

            collectionManager.removeFromCollection(ticketToRemove);
            shell.println("Билет удален по id");
            return true;
        } catch (WrongAmountOfElementsException e) {
            shell.printErr("Выполняется " + getName());
        } catch (CollectionIsEmptyException e) {
            shell.printErr("Коллекция пуста");
        } catch (NumberFormatException e) {
            shell.printErr("id должно быть представлено числом");
        } catch (NotFoundException e) {
            shell.printErr("билета с таким id не существует");
        }
        return false;
    }
}
