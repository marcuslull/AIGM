package com.marcuslull.aigm.comms;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.marcuslull.aigm.comms.infrastructure.AIGMPayload;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "@type")
@JsonSubTypes({@JsonSubTypes.Type(value = AIGMPayload.class, name = "AIGMPayload")})
public interface Payload {
    UUID getUUID();
    Instant getCreated();
    String getReceiver();
    String getSender();
    Map<String, String> getData();
}
