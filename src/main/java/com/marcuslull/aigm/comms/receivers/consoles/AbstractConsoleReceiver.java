package com.marcuslull.aigm.comms.receivers.consoles;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.enums.ConsoleNameEnum;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.Scanner;

@Service
public abstract class AbstractConsoleReceiver implements Receiver {
    protected final Scanner scanner;
    protected ConsoleNameEnum name;
    protected PrintStream printStream;

    public AbstractConsoleReceiver() {
        this.scanner = new Scanner(System.in);
    }

    public ConsoleNameEnum getName() {
        return name;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }
}
