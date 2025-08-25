package dev.scanysses.auto_1e7_stepping.client;

import dev.scanysses.auto_1e7_stepping.Auto1e7Config;
import dev.scanysses.auto_1e7_stepping.PositioningMode;
import net.minecraft.client.MinecraftClient;

import java.util.LinkedList;
import java.util.Queue;

public class PositioningManager {

    private final MinecraftClient client;
    private static final Queue<PositioningStep> steps = new LinkedList<>();
    private int waitTicks = 0;

    public PositioningManager(MinecraftClient client) {
        this.client = client;
    }

    public void executePositioning() {
        PositioningMode mode = Auto1e7Config.HANDLER.instance().positioningMode;
        steps.clear();
        waitTicks = 0;

        switch (mode) {
            case PACKET -> new PacketPositioning().executeAxisPush(client);
            case SCRIPT -> new ScriptPositioning().executeScriptMode(client, steps);
        }
    }

    public void tick() {
        if (waitTicks > 0) {
            waitTicks--;
            return;
        }
        if (!steps.isEmpty()) {
            PositioningStep step = steps.poll();
            waitTicks = step.getDelay();
            step.run();
        }
    }

}
