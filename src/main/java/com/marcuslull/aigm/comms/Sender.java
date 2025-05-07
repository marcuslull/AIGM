package com.marcuslull.aigm.comms;

import java.util.List;

public interface Sender extends DirectoryNode {
    List<Router> getRouters();
    void send(Package pkg);
}
