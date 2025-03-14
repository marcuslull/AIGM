package com.marcuslull.aigm_router.legacy;

import com.marcuslull.aigm_router.model.AIMessagePriority;
import com.marcuslull.aigm_router.model.AIName;

import java.time.Instant;
import java.util.UUID;

public record Deferral(
        UUID deferralId,
        DeferralTypes deferralTypes,
        AIName sourceAI,
        AIName targetAI,
        AIMessagePriority priority,
        DeferralData data,
        DeferralContext context,
        String relatedDeferralIds,
        double confidenceScore
){

    static Instant timeStamp = Instant.now();

    @Override
    public String toString() {
        return "Deferral{" +
                "deferralId=" + deferralId +
                ", deferralTypes=" + deferralTypes +
                ", sourceAI=" + sourceAI +
                ", targetAI=" + targetAI +
                ", priority=" + priority +
                ", data=" + data +
                ", context=" + context +
                ", relatedDeferralIds=" + relatedDeferralIds +
                ", confidenceScore=" + confidenceScore +
                ", timeStamp=" + timeStamp +
                '}';
    }
}
