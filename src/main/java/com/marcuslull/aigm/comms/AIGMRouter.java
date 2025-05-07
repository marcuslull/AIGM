package com.marcuslull.aigm.comms;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AIGMRouter implements Router{

    // Context class for strategy design pattern - receiver interface = Receiver
    private final List<Receiver> receivers;

    public AIGMRouter() {
        this.receivers = new ArrayList<>();
    }

    @Override
    public List<Receiver> getReceiverList() {
        return receivers;
    }

    @Override
    public void route(Package pkg) {
        // TODO: iterate through receivers
    }

    @Override
    public void addReceiver(Receiver receiver) {
        this.receivers.add(receiver);
    }

    @Override
    public void removeReceiver(Receiver receiver) {
        this.receivers.remove(receiver);
    }

    @Override
    public void registerWithDirectory() {
        Directory.addRouter(this);
    }
}
