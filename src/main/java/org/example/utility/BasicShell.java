package org.example.utility;

public interface BasicShell {
    void print(Object obj);
    void println(Object obj);
    void printErr(Object obj);
    void printTable(Object obj1, Object obj2);
    void ps1();
    void ps2();
    String getPS1();
    String getPS2();

}
