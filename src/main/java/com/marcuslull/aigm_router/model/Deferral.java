package com.marcuslull.aigm_router.model;

import java.util.UUID;

public record Deferral(
        UUID deferralId,
        DeferralType deferralType,
        AIModelType sourceAI,
        AIModelType targetAI,
        DeferralPriority priority,
        DeferralData data,
        DeferralContext context,
        UUID[] relatedDeferralIds,
        double confidenceScore
){}
