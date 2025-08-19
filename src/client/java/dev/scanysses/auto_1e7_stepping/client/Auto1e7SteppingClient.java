package dev.scanysses.auto_1e7_stepping.client;

import dev.scanysses.auto_1e7_stepping.Auto1e7Config;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Auto1e7SteppingClient implements ClientModInitializer {
    private static final Logger LOG = LogManager.getLogger("auto1e7");

    private PositioningManager positioningManager;

    @Override
    public void onInitializeClient() {
        LOG.info("auto 1e7 client initialization - loading config");
        Auto1e7Config.load();

        Auto1e7ClientCommand.register();

        Auto1e7ClientKeyBinds.register();

        positioningManager = new PositioningManager(MinecraftClient.getInstance());

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && positioningManager != null) {
                positioningManager.tick();
            }
        });
    }
}