package org.example.managers;

import org.example.commands.Command;
import org.example.exceptions.ScriptRecursionException;
import org.example.managers.CommandManager;
import org.example.utility.ExecuteStatus;
import org.example.utility.Shell;
import org.example.utility.UserInput;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Executor {
    private Shell shell;
    private CommandManager commandManager;
    private List<String> scriptStack = new ArrayList<>();

    public Executor(Shell shell, CommandManager commandManager) {
        this.shell = shell;
        this.commandManager = commandManager;
    }

    public void userMode() {
        Scanner userScanner = UserInput.getUserScanner();

        try {
            ExecuteStatus commandStatus;
            String[] userCommand = {"", ""};

            do {
                shell.ps1();
                userCommand = (userScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                //commandManager.addToHistory(userCommand[0]);
                commandStatus = startCommand(userCommand);
            } while (commandStatus != ExecuteStatus.EXIT);

        } catch (NoSuchElementException e) {
            shell.printErr("Пользователь ничего не ввел");
        } catch (IllegalStateException e) {
            shell.printErr("ошибка");
        }
    }

    public ExecuteStatus scriptMode(String argument) {
        String[] userCommand = {"", ""};
        ExecuteStatus commandStatus;
        scriptStack.add(argument);
        if (!new File(argument).exists()) {
            argument = "../" + argument;
        }
        try (Scanner scriptScanner = new Scanner(new File(argument))) {
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            Scanner tmpScanner = UserInput.getUserScanner();
            UserInput.setUserScanner(scriptScanner);
            UserInput.setFileMode();

            do {
                userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (scriptScanner.hasNextLine() && userCommand[0].isEmpty()) {
                    userCommand = (scriptScanner.nextLine().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                shell.println(shell.getPS1() + String.join(" ", userCommand));
                if (userCommand[0].equals("execute_script")) {
                    for (String script : scriptStack) {
                        if (userCommand[1].equals(script)) throw new ScriptRecursionException();
                    }
                }
                commandStatus = startCommand(userCommand);
            } while (commandStatus == ExecuteStatus.OK && scriptScanner.hasNextLine());

            UserInput.setUserScanner(tmpScanner);
            UserInput.setUserMode();

            if (commandStatus == ExecuteStatus.ERROR && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                shell.println("Проверьте скрипт на корректность введенных данных!");
            }

            return commandStatus;

        } catch (FileNotFoundException exception) {
            shell.printErr("Файл со скриптом не найден!");
        } catch (NoSuchElementException exception) {
            shell.printErr("Файл со скриптом пуст!");
        } catch (ScriptRecursionException exception) {
            shell.printErr("Скрипты не могут вызываться рекурсивно!");
        } catch (IllegalStateException exception) {
            shell.printErr("Непредвиденная ошибка!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return ExecuteStatus.ERROR;
    }


    private ExecuteStatus startCommand(String[] userCommand) {
        if (userCommand[0].equals("")) return ExecuteStatus.OK;
        Command command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) {
            shell.printErr("Команда " + userCommand[0] + " не найдена");
            return ExecuteStatus.ERROR;
        }

        switch (userCommand[0]) {
            case "exit" -> {
                if (!commandManager.getCommands().get("exit").execute(userCommand)) return ExecuteStatus.ERROR;
                else return ExecuteStatus.EXIT;
            }
            case "execute_script" -> {
                if (!commandManager.getCommands().get("execute_script").execute(userCommand)) return ExecuteStatus.ERROR;
                else  return scriptMode(userCommand[1]);
            }
            default -> {if (!command.execute(userCommand)) return ExecuteStatus.ERROR; }
        };
        return ExecuteStatus.OK;
    }
}
