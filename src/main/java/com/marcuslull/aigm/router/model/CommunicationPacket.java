package com.marcuslull.aigm.router.model;

import com.marcuslull.aigm.data.model.LedgerSearch;
import com.marcuslull.aigm.data.model.ResonanceSearch;
import com.marcuslull.aigm.messaging.model.GroupMessage;
import com.marcuslull.aigm.messaging.player.model.PlayerMessage;

public record CommunicationPacket(
        String author,
        PlayerMessage playerMessage,
        GroupMessage groupMessage,
        ResonanceSearch resonanceSearch,
        LedgerSearch ledgerSearch) {

    public boolean hasPlayerMessage() {
        return (playerMessage != null);
    }

    public boolean hasGroupMessage() {
        return (groupMessage != null);
    }

    public boolean hasResonanceSearch() {
        return (resonanceSearch != null);
    }

    public boolean hasLedgerSearch() {
        return (ledgerSearch != null);
    }

    public boolean hasPlayerMessageOnly() {
        return !(this.hasGroupMessage() || this.hasResonanceSearch() || this.hasLedgerSearch());
    }

    public boolean hasGroupMessageOnly() {
        return !(this.hasPlayerMessage() || this.hasResonanceSearch() || this.hasLedgerSearch());
    }
}
