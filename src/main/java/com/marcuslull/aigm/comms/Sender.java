package com.marcuslull.aigm.comms;

import java.util.List;

public interface Sender {
    List<Router> getRouterList();
    Package packagePayload(List<Payload> payload);
    void send(Package pkg);
}
