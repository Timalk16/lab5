package org.example.forms;

import org.example.exceptions.FormException;
import org.example.exceptions.ScriptInputException;
import org.example.models.Coordinates;
import org.example.utility.Shell;
import org.example.utility.UserInput;

import java.util.NoSuchElementException;

public class CoordinatesForm extends Form<Coordinates> {
    private Shell shell;

    public CoordinatesForm(Shell shell) {
        this.shell = shell;
    }


    @Override
    public Coordinates build() throws FormException, ScriptInputException {
        Coordinates coordinates = new Coordinates(askX(), askY());
        if (!coordinates.validate()) throw new FormException();
        return coordinates;
    }

    public Long askX() throws ScriptInputException {
        var fileMode = UserInput.fileMode();
        long x;
        while (true) {
            try {
                shell.println("Введите координату X:");
                shell.ps2();
                var strX = UserInput.getUserScanner().next().trim();
                if (fileMode) shell.println(strX);

                x = Long.parseLong(strX);
                break;
            } catch (NoSuchElementException exception) {
                shell.printErr("Координата X не распознана!");
                if (fileMode) throw new ScriptInputException();
            } catch (NumberFormatException exception) {
                shell.printErr("Координата X должна быть представлена числом!");
                if (fileMode) throw new ScriptInputException();
            } catch (NullPointerException | IllegalStateException exception) {
                shell.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return x;
    }

    public int askY() throws ScriptInputException {
        var fileMode = UserInput.fileMode();
        int y;
        while (true) {
            try {
                shell.println("Введите координату Y:");
                shell.ps2();
                var strX = UserInput.getUserScanner().next().trim();
                if (fileMode) shell.println(strX);

                y = Integer.parseInt(strX);
                break;
            } catch (NoSuchElementException exception) {
                shell.printErr("Координата X не распознана!");
                if (fileMode) throw new ScriptInputException();
            } catch (NumberFormatException exception) {
                shell.printErr("Координата X должна быть представлена числом!");
                if (fileMode) throw new ScriptInputException();
            } catch (NullPointerException | IllegalStateException exception) {
                shell.printErr("Непредвиденная ошибка!");
                System.exit(0);
            }
        }
        return y;
    }
}
