package dev.scanysses.auto_1e7_stepping.client;

import net.minecraft.client.MinecraftClient;

public class YawAlign {

    public static float alignToAxis(MinecraftClient client) {

        assert client.player != null;
        float alignedYaw = Math.round(client.player.getYaw() / 90f) * 90f;
        client.player.setYaw(alignedYaw);

        return alignedYaw;
    }
}
