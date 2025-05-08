package com.marcuslull.aigm.comms.receivers.data;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;
import com.marcuslull.aigm.comms.directory.Directory;
import com.marcuslull.aigm.comms.enums.DataNameEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VectorDatabaseService extends AbstractDataReceiver implements Sender {

    // from super: DataNameEnum name, Map<String, String> connectionInformation, Map<String, String> queryProperties
    private final Logger logger = LoggerFactory.getLogger(VectorDatabaseService.class);


    @Override
    public boolean canHandle(Payload payload) {
        return DataNameEnum.fromString(payload.getReceiver()) == DataNameEnum.RESONANCE;
    }

    @Override
    public void handle(Payload payload) {
        logger.info("{} - Handling Payload: {}", this.getClass().getSimpleName(), payload.getUUID());
    }

    @Override
    public List<Router> getRouters() {
        return Directory.getRouters();
    }

    @Override
    public void send(Package pkg) {
        // TODO: select a router from getRouters() and send
    }

    @Override
    public void registerWithDirectory() {
        logger.info("{} - Registering with Directory", this.getClass().getSimpleName());
        Directory.addReceiver(this);
        Directory.addSender(this);
    }
}
