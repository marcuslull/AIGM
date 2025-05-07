package com.marcuslull.aigm.comms.receivers;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.enums.ToolNameEnum;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.List;

@Service
public abstract class AbstractToolReceiver implements Receiver {
    protected ToolNameEnum name;
    protected List<Method> methods;

    public ToolNameEnum getName() {
        return name;
    }

    public List<Method> getMethods() {
        return methods;
    }
}
