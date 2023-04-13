package org.example.forms;

import org.example.exceptions.*;
import org.example.models.Event;
import org.example.managers.CollectionManager;
import org.example.utility.Shell;
import org.example.utility.UserInput;

import java.time.LocalDate;

import java.util.NoSuchElementException;

public class EventForm extends Form<Event> {
    private Shell shell;
    private CollectionManager collectionManager;
    private final int MIN_AGE = 1;

    public EventForm(Shell shell, CollectionManager collectionManager) {
        this.shell = shell;
        this.collectionManager = collectionManager;
    }

    @Override
    public Event build() throws FormException, ScriptInputException {
        shell.println("Введите null, чтобы оставить событие пустым или id определенной организации");
        shell.ps2();

        var fileMode = UserInput.fileMode();
        String input = UserInput.getUserScanner().nextLine().trim();
        if (fileMode) shell.println(input);
        if (input.equals("null")) return null;

        try {
            if (input.startsWith("id=") || input.startsWith("ID=")) {
                input = input.replaceFirst("^(id=|ID=)", "");
                Long id = Long.parseLong(input);
                if (id < 1) throw new LimitException();

                Event event = Event.byId(id);
                if(event == null) throw new WrongAmountOfElementsException();
                return event;
            }
        } catch (LimitException e) {
            shell.printErr("Id должно быть больше нуля");
            if (fileMode) throw new ScriptInputException();
        } catch (NumberFormatException e) {
            shell.printErr("ID должно быть числом");
            if (fileMode) throw new ScriptInputException();
        } catch (NullPointerException | IllegalStateException e) {
            shell.printErr("Непредвиденная ошибка");
        } catch (WrongAmountOfElementsException e) {
            shell.printErr("События с таким id не существует");
        }

        shell.println("Создание нового события");
        Event event = new Event(
                askName(),
                LocalDate.now(),
                askMinAge(),
                askDescription()
        );
        if (!event.validate()) throw new FormException();
        return event;

    }

    private String askName() throws ScriptInputException {
        String name;
        var fileMode = UserInput.fileMode();
        while (true) {
            try {
                shell.println("Введите название события");
                shell.ps2();

                name = UserInput.getUserScanner().nextLine().trim();
                if (fileMode) shell.println(name);
                if (name.equals("")) throw new MustBeNotEmpyException();
                break;
            } catch (NoSuchElementException e) {
                shell.println("Название события не распознано");
                if (fileMode) throw new ScriptInputException();
            } catch (MustBeNotEmpyException e) {
                shell.println("Названия события не может быть пустым");
                if (fileMode) throw new ScriptInputException();
            } catch (IllegalStateException e) {
                shell.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return name;
    }

    private int askMinAge() throws ScriptInputException {
        int minAge;
        var fileMode = UserInput.fileMode();
        while (true) {
            try {
                shell.println("Введите желаемый минимальный возраст для события");
                shell.ps2();

                var strMinAge = UserInput.getUserScanner().nextLine().trim();
                if (fileMode) shell.println(strMinAge);

                minAge = Integer.parseInt(strMinAge);
                if (minAge < MIN_AGE) throw new LimitException();
                break;
            } catch (NoSuchElementException e) {
                shell.println("Неизвестный возраст");
                if (fileMode) throw new ScriptInputException();
            } catch (LimitException e) {
                shell.println("Минимальный возраст должен быть больше 0");
                if (fileMode) throw new ScriptInputException();
            } catch (NumberFormatException e) {
                shell.println("Возраст должен быть записан числом");
                if (fileMode) throw new ScriptInputException();
            } catch (NullPointerException | IllegalStateException e) {
                shell.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return minAge;
    }

    private String askDescription() throws ScriptInputException {
        String description;
        var fileMode = UserInput.fileMode();
        while (true) {
            try {
                shell.println("Введите описание события");
                shell.ps2();

                description = UserInput.getUserScanner().nextLine().trim();
                if (fileMode) shell.println(description);
                if (description.equals("")) throw new MustBeNotEmpyException();
                break;
            } catch (NoSuchElementException e) {
                shell.println("Описание события не распознано");
                if (fileMode) throw new ScriptInputException();
            } catch (MustBeNotEmpyException e) {
                shell.println("Описание события не может быть пустым");
                if (fileMode) throw new ScriptInputException();
            } catch (IllegalStateException e) {
                shell.println("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return description;
    }
}
