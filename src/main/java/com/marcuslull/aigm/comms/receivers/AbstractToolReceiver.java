package com.marcuslull.aigm.comms.receivers;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.enums.ToolNameEnum;

import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractToolReceiver implements Receiver {
    private ToolNameEnum name;
    private List<Method> methods;

    public AbstractToolReceiver(ToolNameEnum name, List<Method> methods) {
        this.name = name;
        this.methods = methods;
    }

    public ToolNameEnum getName() {
        return name;
    }

    public List<Method> getMethods() {
        return methods;
    }
}
