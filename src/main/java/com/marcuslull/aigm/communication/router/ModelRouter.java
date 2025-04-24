package com.marcuslull.aigm.communication.router;

import com.marcuslull.aigm.communication.destinations.Destination;
import com.marcuslull.aigm.communication.protocol.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelRouter implements Router {

    private static final Logger logger = LoggerFactory.getLogger(ModelRouter.class);

    // Context class for strategy design pattern - handler interface = CommunicationHandler
    public final static List<Destination> models = new ArrayList<>();

    @Override
    public void addDestination(Destination destination) {
        if (destination != null) {
            models.add(destination);
            logger.debug("NEW MODEL REGISTERED: {}", destination.getClass().getSimpleName());
        }

    }

    @Override
    public void removeDestination(Destination destination) {
        if (destination != null) {
            models.remove(destination);
            logger.debug("MODEL DE-REGISTERED: {}", destination.getClass().getSimpleName());
        }
    }

    @Override
    public Packet route(Packet packet) {
        if (models.isEmpty()) throw new RuntimeException("No response handlers available");
        if(!packet.hasPayloadCollection()) throw new RuntimeException("No payload found in packet");


        // match payloads to models and send
        packet.getPayloadCollection().forEach(payload ->
                models.stream().filter(model ->
                        model.canHandle(payload)).findAny().orElseThrow().handleAsync(payload));

        return packet;
    }


//    public CommunicationPacket send(CommunicationPacket communicationPacket) {
//
//        // We need to know what AI to send the communication to
//        ChatClient client;
//        if (communicationPacket.hasGroupMessage()) { // destination will be the target of the group communication
//            client = AIClientGroup.getModel(communicationPacket.getGroupMessage().getTarget());
//        } else if (communicationPacket.hasResonanceSearch() || communicationPacket.hasLedgerSearch()) { // dest will be self
//            client = AIClientGroup.getModel(AIName.valueOf(communicationPacket.getAuthor()));
//        } else { // destination will always be Oratorix on playerChat
//            client = AIClientGroup.getModel(AIName.ORATORIX);
//        }
//
//        // Spring AI ChatClient expects text and filters on `{` or `}` for their own parsing.
//        // The AIs respond in markdown JSON
//        try {
//            String jsonString = objectMapper.writeValueAsString(communicationPacket); // convert to JSON
//            communicationPacket = client.prompt().user(escapeJsonBrackets(jsonString)).call().entity(CommunicationPacket.class);
//        } catch (JsonProcessingException e) {
//            //TODO: Handle this
//            throw new RuntimeException(e);
//        }
//        return communicationPacket;
//    }
//
//    private String escapeJsonBrackets(String message) {
//        // The Spring AI ChatClient parser filters on `{` and `}` so we need to escape ours.
//        return message.replace("{", "\\{").replace("}", "\\}");
//    }


}
