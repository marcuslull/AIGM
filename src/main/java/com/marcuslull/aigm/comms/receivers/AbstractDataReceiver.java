package com.marcuslull.aigm.comms.receivers;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.enums.DataNameEnum;

import java.util.Map;

public abstract class AbstractDataReceiver implements Receiver {
    private DataNameEnum name;
    private Map<String, String> connectionInformation;
    private Map<String, String> queryProperties;

    public AbstractDataReceiver(DataNameEnum name, Map<String, String> connectionInformation) {
        this.name = name;
        this.connectionInformation = connectionInformation;
    }

    public DataNameEnum getName() {
        return name;
    }

    public Map<String, String> getConnectionInformation() {
        return connectionInformation;
    }

    public Map<String, String> getQueryProperties() {
        return queryProperties;
    }

    public void setQueryProperties(Map<String, String> queryProperties) {
        this.queryProperties = queryProperties;
    }
}
