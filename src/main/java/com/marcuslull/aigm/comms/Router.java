package com.marcuslull.aigm.comms;

import com.marcuslull.aigm.comms.directory.DirectoryNode;

import java.util.List;

public interface Router extends DirectoryNode {
    List<Receiver> getReceiverList();
    void route(Package pkg);
}
