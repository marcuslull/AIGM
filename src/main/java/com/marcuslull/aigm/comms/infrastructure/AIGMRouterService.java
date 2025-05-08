package com.marcuslull.aigm.comms.infrastructure;

import com.marcuslull.aigm.comms.Package;
import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.directory.Directory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AIGMRouterService implements Router {

    @Override
    public List<Receiver> getReceiverList() {
        return Directory.getReceivers();
    }

    @Override
    public void route(Package pkg) {
        // TODO: iterate through receivers via getReceivers()
    }

    @Override
    public void registerWithDirectory() {
        Directory.addRouter(this);
    }
}
