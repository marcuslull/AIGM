package com.marcuslull.aigm.comms.receivers.data;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Payload;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;
import com.marcuslull.aigm.comms.directory.Directory;
import com.marcuslull.aigm.comms.enums.DataNameEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StructuredDatabaseService extends AbstractDataReceiver implements Sender {

    // from super: DataNameEnum name, Map<String, String> connectionInformation, Map<String, String> queryProperties

    @Override
    public boolean canHandle(Payload payload) {
        return DataNameEnum.fromString(payload.getReceiver()) == DataNameEnum.LEDGER;
    }

    @Override
    public void handle(Payload payload) {
        // TODO: compose and send query
    }

    @Override
    public List<Router> getRouters() {
        return Directory.getRouters();
    }

    @Override
    public void send(Package pkg) {
        // TODO: Send using getRouters()
    }

    @Override
    public void registerWithDirectory() {
        Directory.addReceiver(this);
        Directory.addSender(this);
    }
}
