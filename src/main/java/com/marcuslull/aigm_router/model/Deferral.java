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
) implements Runnable {
    @Override
    public void run() {
        // TODO figure out how to store existing AI instances
        // TODO figure out how tooling sends and receives data.
    }
}
