package com.marcuslull.aigm_router.model;

import java.util.UUID;

public record Deferral(
        UUID deferralId,
        DeferralTypes deferralTypes,
        AIClientTypes sourceAI,
        AIClientTypes targetAI,
        DeferralPriorities priority,
        DeferralData data,
        DeferralContext context,
        String relatedDeferralIds,
        double confidenceScore
){
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
                '}';
    }
}
