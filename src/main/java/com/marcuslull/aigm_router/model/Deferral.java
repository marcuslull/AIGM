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
){

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"deferralId\":\"").append(deferralId).append("\",");
        sb.append("\"deferralType\":\"").append(deferralType).append("\",");
        sb.append("\"sourceAI\":\"").append(sourceAI).append("\",");
        sb.append("\"targetAI\":\"").append(targetAI).append("\",");
        sb.append("\"priority\":\"").append(priority).append("\",");
        sb.append("\"data\":").append(data).append(","); // Assuming DeferralData's toString is JSON-like
        sb.append("\"context\":").append(context).append(","); // Assuming DeferralContext's toString is JSON-like
        sb.append("\"relatedDeferralIds\":[");
        if (relatedDeferralIds != null) {
            for (int i = 0; i < relatedDeferralIds.length; i++) {
                sb.append("\"").append(relatedDeferralIds[i]).append("\"");
                if (i < relatedDeferralIds.length - 1) {
                    sb.append(",");
                }
            }
        }
        sb.append("],");
        sb.append("\"confidenceScore\":").append(confidenceScore);
        sb.append("}");
        return sb.toString();
    }
}
