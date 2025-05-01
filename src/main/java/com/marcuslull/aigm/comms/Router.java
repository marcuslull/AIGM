package com.marcuslull.aigm.comms;

import java.util.List;

public interface Router {
    List<Receiver> getReceiverList();
    void route(Package pkg);
}
