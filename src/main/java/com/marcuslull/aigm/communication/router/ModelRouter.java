package com.marcuslull.aigm.communication.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.marcuslull.aigm.communication.protocol.Packet;
import com.marcuslull.aigm.gm.model.AIClientGroup;
import com.marcuslull.aigm.gm.model.enums.AIName;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelRouter implements Router {

    // Context class for strategy design pattern - handler interface = CommunicationHandler
    public final static List<Destination> models = new ArrayList<>();


    @Override
    public void addDestination(Destination destination) {
        models.add(destination);

    }

    @Override
    public void removeDestination(Destination destination) {
        models.remove(destination);
    }

    @Override
    public Packet route(Packet packet) {
        return null;
    }

    // TODO: Refactor everything below this line to the methods above.


    public CommunicationPacket send(CommunicationPacket communicationPacket) {

        // We need to know what AI to send the communication to
        ChatClient client;
        if (communicationPacket.hasGroupMessage()) { // destination will be the target of the group communication
            client = AIClientGroup.getModel(communicationPacket.getGroupMessage().getTarget());
        } else if (communicationPacket.hasResonanceSearch() || communicationPacket.hasLedgerSearch()) { // dest will be self
            client = AIClientGroup.getModel(AIName.valueOf(communicationPacket.getAuthor()));
        } else { // destination will always be Oratorix on playerChat
            client = AIClientGroup.getModel(AIName.ORATORIX);
        }

        // Spring AI ChatClient expects text and filters on `{` or `}` for their own parsing.
        // The AIs respond in markdown JSON
        try {
            String jsonString = objectMapper.writeValueAsString(communicationPacket); // convert to JSON
            communicationPacket = client.prompt().user(escapeJsonBrackets(jsonString)).call().entity(CommunicationPacket.class);
        } catch (JsonProcessingException e) {
            //TODO: Handle this
            throw new RuntimeException(e);
        }
        return communicationPacket;
    }

    private String escapeJsonBrackets(String message) {
        // The Spring AI ChatClient parser filters on `{` and `}` so we need to escape ours.
        return message.replace("{", "\\{").replace("}", "\\}");
    }


}
