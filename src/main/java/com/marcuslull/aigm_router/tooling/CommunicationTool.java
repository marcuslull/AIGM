package com.marcuslull.aigm_router.tooling;

import com.marcuslull.aigm_router.model.*;
import com.marcuslull.aigm_router.service.ModelGroup;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Service
public class CommunicationTool {

    private final ModelGroup modelGroup;

    public CommunicationTool(ModelGroup modelGroup) {
        this.modelGroup = modelGroup;
    }

    @Tool(
            name = "sendDeferral",
            description = """
                     Send a deferral object to another AI in your group.
                     The deferral must be created by you.
                     This deferral will be used to pass off work to another model.
                     The deferralId should be a UUID.
                     The sourceAI should be the AI model making the request.
                     The targetAI should be the AI model receiving the deferral.
                     The targetAI should be one of: ORATORIX, CHRONOS, ORBIS, JUSTIVOR.
                     The deferralType should be one of: INFORMATION_REQUEST, ACTION_VALIDATION, EVENT_TRIGGER, DIALOGUE_GENERATION, COMBAT_RESOLUTION, WORLD_STATE_UPDATE, OTHER.
                     The priority should be one of: LOW, MEDIUM, HIGH.
                     The data should be in JSON format with the type 'object'.
                     The context should be in JSON format with the type 'object'.
                     The relatedDeferralIds should be in JSON format with the type 'array' and items of type 'string'
                    """
    )
    public Map<String, String> sendDeferral(
            @ToolParam(description = "The ID of the deferral. Must be a UUID.") String deferralId,
            @ToolParam(description = "The AI model making the request.  Must be one of: ORATORIX, CHRONOS, ORBIS, JUSTIVOR.") String sourceAI,
            @ToolParam(description = "The AI model receiving the deferral. Must be one of: ORATORIX, CHRONOS, ORBIS, JUSTIVOR.") String targetAI,
            @ToolParam(description = "The type of deferral. Must be one of: INFORMATION_REQUEST, ACTION_VALIDATION, EVENT_TRIGGER, DIALOGUE_GENERATION, COMBAT_RESOLUTION, WORLD_STATE_UPDATE, OTHER.") String deferralType,
            @ToolParam(description = "The priority of the deferral. Must be one of: LOW, MEDIUM, HIGH.") String priority,
            @ToolParam(description = "The data to send with the deferral.") String data,
            @ToolParam(description = "The context of the deferral.") String context,
            @ToolParam(description = "The IDs of related deferrals.") String relatedDeferralIds,
            @ToolParam(description = "The AI models confidence in this deferral. Must be a floating point number in the form: 1.0, 0.7, etc...") String confidenceScore
    ) {

        System.out.println("Communication tool used!");
        // Build deferral
        Deferral deferral = new Deferral(
                UUID.fromString(deferralId),
                DeferralType.valueOf(deferralType),
                AIModelType.fromString(sourceAI),
                AIModelType.fromString(targetAI),
                DeferralPriority.valueOf(priority),
                new DeferralData(data),
                new DeferralContext(context),
                parseUUIDArray(relatedDeferralIds),
                Double.parseDouble(confidenceScore)
        );
        System.out.println("Communication Tool Call: " + deferral);
        System.out.println("Target AI: " + deferral.targetAI());

        ChatClient targetAIChatClient = modelGroup.getModel(deferral.targetAI());
        System.out.println(modelGroup.toString());
        System.out.println("targetAIChatClient = " + targetAIChatClient);
        new Thread(() -> {
            targetAIChatClient.prompt().user(deferral.toString()).call();
        }).start();

        return Map.of("Result", "Deferral sent");
    }

    private UUID[] parseUUIDArray(String jsonArray) {
        if (jsonArray == null || jsonArray.trim().isEmpty() || jsonArray.trim().equals("[]")) {
            return new UUID[0];
        }
        jsonArray = jsonArray.replaceAll("[^\\w\\-,\\[\\]]", "");
        String[] uuidStrings = jsonArray.substring(1, jsonArray.length() - 1).split(",");
        UUID[] uuids = new UUID[uuidStrings.length];
        for (int i = 0; i < uuidStrings.length; i++) {
            uuids[i] = UUID.fromString(uuidStrings[i]);
        }
        return uuids;
    }
}
