package com.marcuslull.aigm.comms.directory;

import com.marcuslull.aigm.comms.Receiver;
import com.marcuslull.aigm.comms.Router;
import com.marcuslull.aigm.comms.Sender;

import java.util.ArrayList;
import java.util.List;

public interface Directory {
    List<Router> routers = new ArrayList<>();
    List<Receiver> receivers = new ArrayList<>();
    List<Sender> senders = new ArrayList<>();

    public static List<Router> getRouters() {
        return routers;
    }

    public static List<Receiver> getReceivers() {
        return receivers;
    }

    public static List<Sender> getSenders() {
        return senders;
    }

    public static void addRouter(Router router) {
        routers.add(router);
    }

    public static void addReceiver(Receiver receiver) {
        receivers.add(receiver);
    }

    public static void addSender(Sender sender) {
        senders.add(sender);
    }
}
