package com.marcuslull.aigm.communication.router;


import com.marcuslull.aigm.communication.destinations.Destination;
import com.marcuslull.aigm.communication.protocol.Packet;

public interface Router {
    void addDestination(Destination destination);
    void removeDestination(Destination destination);
    Packet route(Packet packet);
}
