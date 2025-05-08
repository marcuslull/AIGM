package com.marcuslull.aigm.comms.receivers.consoles;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.enums.ConsoleNameEnum;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public abstract class AbstractConsoleReceiver implements Receiver {
    protected ConsoleNameEnum name;
    protected PrintStream printStream;

    public ConsoleNameEnum getName() {
        return name;
    }

    public PrintStream getPrintStream() {
        return printStream;
    }
}
