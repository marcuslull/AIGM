package com.marcuslull.aigm.comms.receivers;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.enums.ConsoleNameEnum;

import java.io.PrintStream;

public abstract class AbstractConsoleReceiver implements Receiver {
    private ConsoleNameEnum name;
    private PrintStream printStream;

    public AbstractConsoleReceiver(ConsoleNameEnum name, PrintStream printStream) {
        this.name = name;
        this.printStream = printStream;
    }

    public ConsoleNameEnum getName() {
        return name;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }
}
