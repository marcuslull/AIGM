package com.marcuslull.aigm.router.model;

import com.marcuslull.aigm.data.ledger.model.LedgerSearch;
import com.marcuslull.aigm.data.resonance.model.ResonanceSearch;
import com.marcuslull.aigm.messaging.group.model.GroupMessage;
import com.marcuslull.aigm.messaging.player.model.PlayerMessage;

public class CommunicationPacket {

    private String author;
    private PlayerMessage playerMessage;
    private GroupMessage groupMessage;
    private ResonanceSearch resonanceSearch;
    private LedgerSearch ledgerSearch;

    public CommunicationPacket() {
    }

    public CommunicationPacket(String author, PlayerMessage playerMessage, GroupMessage groupMessage, ResonanceSearch resonanceSearch, LedgerSearch ledgerSearch) {
        this.author = author;
        this.playerMessage = playerMessage;
        this.groupMessage = groupMessage;
        this.resonanceSearch = resonanceSearch;
        this.ledgerSearch = ledgerSearch;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public PlayerMessage getPlayerMessage() {
        return playerMessage;
    }

    public void setPlayerMessage(PlayerMessage playerMessage) {
        this.playerMessage = playerMessage;
    }

    public GroupMessage getGroupMessage() {
        return groupMessage;
    }

    public void setGroupMessage(GroupMessage groupMessage) {
        this.groupMessage = groupMessage;
    }

    public ResonanceSearch getResonanceSearch() {
        return resonanceSearch;
    }

    public void setResonanceSearch(ResonanceSearch resonanceSearch) {
        this.resonanceSearch = resonanceSearch;
    }

    public LedgerSearch getLedgerSearch() {
        return ledgerSearch;
    }

    public void setLedgerSearch(LedgerSearch ledgerSearch) {
        this.ledgerSearch = ledgerSearch;
    }

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

    @Override
    public String toString() {
        return "CommunicationPacket{" +
                "author='" + author + '\'' +
                ", playerMessage=" + playerMessage +
                ", groupMessage=" + groupMessage +
                ", resonanceSearch=" + resonanceSearch +
                ", ledgerSearch=" + ledgerSearch +
                '}';
    }
}