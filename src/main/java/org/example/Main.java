package org.example;

import org.example.commands.*;
import org.example.managers.CollectionManager;
import org.example.managers.CommandManager;
import org.example.managers.Executor;
import org.example.managers.FileManager;
import org.example.models.Event;
import org.example.models.Ticket;
import org.example.utility.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        UserInput.setUserScanner(new Scanner(System.in));
        Shell shell = new Shell();

        if (args.length == 0) {
            shell.println("Введите имя файла");
            System.exit(1);
        }
        String fileName = args[0];
        FileManager fileManager = new FileManager(fileName, shell);
        CollectionManager collectionManager = new CollectionManager(fileManager);

        Ticket.updateNextId(collectionManager);
        Event.updateNextId(collectionManager);

        collectionManager.validateAll(shell);

        CommandManager commandManager = new CommandManager() {{
            addCommand("help", new HelpCommand(shell, this));
            addCommand("add", new AddCommand(shell, collectionManager));
            addCommand("save", new SaveCommand(shell, collectionManager, fileName));
            addCommand("show", new ShowCommand(shell, collectionManager));
            addCommand("info", new InfoCommand(shell, collectionManager));
            addCommand("clear", new ClearCommand(shell, collectionManager));
            addCommand("exit", new ExitCommand(shell));
            addCommand("update", new UpdateIdCommand(shell, collectionManager));
            addCommand("remove_by_id", new RemoveByIdCommand(shell, collectionManager));
            addCommand("execute_script", new ExecuteScriptCommand(shell));
            addCommand("shuffle", new ShuffleCommand(shell, collectionManager));
            addCommand("filter_contains_name", new FilterContainsNameCommand(shell, collectionManager));
            addCommand("filter_greater_than_discount", new FilterGreaterDiscountCommand(shell, collectionManager));
            addCommand("print_descending_price", new PrintDescendingPriceCommand(shell, collectionManager));
            addCommand("insert_at_index", new InsertAtIndexCommand(shell, collectionManager));
        }};

        new Executor(shell, commandManager).userMode();
    }
}