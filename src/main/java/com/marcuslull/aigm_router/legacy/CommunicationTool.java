package com.marcuslull.aigm_router.legacy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcuslull.aigm_router.model.AIClientGroup;
import com.marcuslull.aigm_router.model.AIMessagePriority;
import com.marcuslull.aigm_router.model.AIName;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;


@Service
public class CommunicationTool {

    private final AIClientGroup AIClientGroup;
    private final ObjectMapper mapper;

    public CommunicationTool(AIClientGroup AIClientGroup, ObjectMapper mapper) {
        this.AIClientGroup = AIClientGroup;
        this.mapper = mapper;
    }

    @Tool(
            name = "sendDeferral",
            description = """
                     Send a deferral object to another AI in your group.
                     The deferral must be created by you.
                     This deferral will be used to communicate to another model of your group.
                    """
    )
    public Map<String, String> sendDeferral(
            @ToolParam(description = "The ID of the deferral. Must be a UUID.") String deferralId,
            @ToolParam(description = "The AI model making the request.  Must be one of: ORATORIX, CHRONOS, ORBIS, JUSTIVOR.") String sourceAI,
            @ToolParam(description = "The AI model receiving the deferral. Must be one of: ORATORIX, CHRONOS, ORBIS, JUSTIVOR.") String targetAI,
            @ToolParam(description = "The type of deferral. Must be one of: INFORMATION_REQUEST, ACTION_VALIDATION, EVENT_TRIGGER, DIALOGUE_GENERATION, COMBAT_RESOLUTION, WORLD_STATE_UPDATE, FURTHER_INFORMATION, OTHER.") String deferralType,
            @ToolParam(description = "The priority of the deferral. Must be one of: LOW, MEDIUM, HIGH.") String priority,
            @ToolParam(description = "The data to send with the deferral.") String data,
            @ToolParam(description = "The context of the deferral.") String context,
            @ToolParam(description = "The IDs of related deferrals.") String relatedDeferralIds,
            @ToolParam(description = "The AI models confidence in this deferral. Must be a floating point number in the form: 1.0, 0.7, etc...") String confidenceScore
    ) throws JsonProcessingException {

        Deferral deferral = new Deferral(
                UUID.fromString(deferralId),
                DeferralTypes.valueOf(deferralType),
                AIName.fromString(sourceAI),
                AIName.fromString(targetAI),
                AIMessagePriority.valueOf(priority),
                new DeferralData(data),
                new DeferralContext(context),
                mapper.writeValueAsString(relatedDeferralIds),
                Double.parseDouble(confidenceScore)
        );

        System.out.println("\nDEFERRAL: " + deferral);

        ChatClient targetAIChatClient = AIClientGroup.getModel(deferral.targetAI());
        ChatResponse response = targetAIChatClient.prompt().user(deferral.toString()).call().chatResponse();

        System.out.println("\nA successful deferral has been exchanged from: " + deferral.sourceAI() + " to: " + deferral.targetAI());
        System.out.println("\nDEFERRAL RESPONSE: " + response);

        assert response != null;
        return Map.of("Response", response.toString());
    }

}
