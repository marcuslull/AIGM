package com.marcuslull.aigm_router.tooling;

import com.marcuslull.aigm_router.model.Deferral;
import org.springframework.stereotype.Service;

@Service
public class CommunicationTool {

    public void sendDeferral(Deferral deferral) {
            if (deferral != null) {
                Thread thread = new Thread(deferral);
            }
    }
}
