package org.example.utility;

public class Shell implements BasicShell {
    private static final String PS1 = "$ ";
    private static final String PS2 = "> ";
    @Override
    public void print(Object obj) {
        System.out.print(obj);
    }

    @Override
    public void println(Object obj) {
        System.out.println(obj);
    }

    @Override
    public void printErr(Object obj) {
        System.out.println("ошибка: " + obj);
    }

    @Override
    public void printTable(Object obj1, Object obj2) {
        System.out.printf(" %-35s%-1s%n", obj1, obj2);
    }

    @Override
    public void ps1() {
        System.out.println(PS1);
    }

    @Override
    public void ps2() {
        System.out.println(PS2);
    }

    @Override
    public String getPS1() {
        return PS1;
    }

    @Override
    public String getPS2() {
        return PS2;
    }
}
