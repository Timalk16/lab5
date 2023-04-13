package org.example.commands;

import org.example.exceptions.FormException;
import org.example.exceptions.ScriptInputException;
import org.example.exceptions.WrongAmountOfElementsException;
import org.example.forms.TicketForm;
import org.example.managers.CollectionManager;
import org.example.utility.Shell;

public class AddCommand extends Command {
    private Shell shell;
    private CollectionManager collectionManager;


    public AddCommand(Shell shell, CollectionManager collectionManager) {
        super("add {element}", "добавить новый элемент в коллекцию");
        this.shell = shell;
        this.collectionManager = collectionManager;
    }



    @Override
    public boolean execute(String[] args) {
        try {
            if (!args[1].isEmpty()) throw new WrongAmountOfElementsException();
            shell.println("Добавление нового билета");
            collectionManager.addElement((new TicketForm(shell, collectionManager)).build());
            shell.println("Билет успешно добавлен");
            return true;
        } catch (WrongAmountOfElementsException e) {
            shell.printErr("Слишком много аргументов");
            shell.println("Выполняется " + getName());
        } catch (FormException e) {
            shell.println("Поля не валидны");
        } catch (ScriptInputException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
