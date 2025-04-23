package com.marcuslull.aigm.tooling.diceRoller;

import com.marcuslull.aigm.router.CommunicationHandler;
import com.marcuslull.aigm.router.CommunicationSender;
import com.marcuslull.aigm.router.model.CommunicationPacket;
import com.marcuslull.aigm.tooling.diceRoller.service.Roller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiceRoller  implements CommunicationHandler {

    private final CommunicationSender communicationSender;

    public DiceRoller(CommunicationSender communicationSender) {
        this.communicationSender = communicationSender;
    }

    @Override
    public void handle(CommunicationPacket communicationPacket) {
        List<Integer> result = new ArrayList<>();


        // TODO: Debug the Dice Roller

        if (communicationPacket.hasTooling()) {
            System.out.println("DICE ROLL - " + communicationPacket.getTooling().getRoll());
            result = Roller.roll(communicationPacket.getTooling().getRoll());
        }

        System.out.println("DICE ROLL RESULT - " + communicationPacket.getTooling().getRoll());

        assert result != null;
        communicationPacket.getTooling().getRoll().setResult(result.toString());

        communicationSender.send(communicationPacket);
    }

    @Override
    public boolean canHandle(CommunicationPacket communicationPacket) {
        if (communicationPacket.hasTooling()) return communicationPacket.getTooling().hasRoll();
        return false;
    }
}
