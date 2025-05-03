package com.marcuslull.aigm.comms;

import java.util.List;

public interface Sender {
    List<Router> getRouters();
    void send(Package pkg);
}
