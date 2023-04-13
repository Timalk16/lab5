package org.example.commands;

import org.example.exceptions.*;
import org.example.forms.TicketForm;
import org.example.managers.CollectionManager;
import org.example.utility.Shell;

public class InsertAtIndexCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;

    public InsertAtIndexCommand(Shell shell, CollectionManager collectionManager) {
        super("insert_at index {element}", "добавить новый элемент в заданную позицию");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }


    @Override
    public boolean execute(String[] args) {
        try {
            if (args[1].isEmpty()) throw new WrongAmountOfElementsException();


            var index = Integer.parseInt(args[1]);
            if (index < 0 || index > collectionManager.collectionSize()) throw new IndexOutOfBoundsException();
            var ticket = (new TicketForm(shell, collectionManager)).build();
            collectionManager.addElementAtIndex(index, ticket);

            shell.println("Билет успешно добавлен на место " + index);
            return true;
        } catch (WrongAmountOfElementsException e) {
            shell.printErr("Выполняется " + getName());
        } catch (NumberFormatException e) {
            shell.printErr("index должен быть числом");
        } catch (ScriptInputException e) {
            e.printStackTrace();
        } catch (FormException e) {
            shell.printErr("Поля билеты не валидны");
        } catch (IndexOutOfBoundsException e) {
            shell.printErr("index выходит за пределы коллекции");
        }
        return false;

    }
}
