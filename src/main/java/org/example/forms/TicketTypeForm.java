package org.example.forms;

import org.example.exceptions.FormException;
import org.example.exceptions.ScriptInputException;
import org.example.models.TicketType;
import org.example.utility.Shell;
import org.example.utility.UserInput;

import java.util.NoSuchElementException;


public class TicketTypeForm extends Form<TicketType> {
    private final Shell shell;

    public TicketTypeForm(Shell shell) {
        this.shell = shell;
    }
    @Override
    public TicketType build() throws FormException, ScriptInputException {
        var fileMode = UserInput.fileMode();

        String strTicketType;
        TicketType ticketType;

        while (true) {
            try {
                shell.println("Типы билетов - " + TicketType.names());
                shell.println("Выберите тип билета");
                shell.ps2();

                strTicketType = UserInput.getUserScanner().nextLine().trim();
                if (fileMode) shell.println(strTicketType);

                if (strTicketType.equals("") || strTicketType.equals("null")) return null;
                ticketType = TicketType.valueOf(strTicketType.toUpperCase());
                break;
            } catch (NoSuchElementException e) {
                shell.printErr("Тип билета не распознан!");
                if (fileMode) throw new ScriptInputException();
            } catch (IllegalArgumentException e) {
                shell.printErr("Билета такого типа нет в списке");
            } catch (IllegalStateException e) {
                shell.printErr("Непредвиденная ошибка");
                System.exit(0);
            }
        }
        return ticketType;
    }
}
