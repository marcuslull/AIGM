package com.marcuslull.aigm.comms;

import java.util.List;

public interface Router extends DirectoryNode {
    List<Receiver> getReceiverList();
    void route(Package pkg);
    void addReceiver(Receiver receiver);
    void removeReceiver(Receiver receiver);
}
