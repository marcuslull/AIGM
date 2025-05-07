package com.marcuslull.aigm.comms.receivers;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.enums.DataNameEnum;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public abstract class AbstractDataReceiver implements Receiver {
    public DataNameEnum name;
    protected Map<String, String> connectionInformation;
    protected Map<String, String> queryProperties;

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
