package dev.scanysses.auto_1e7_stepping.client;

import net.minecraft.client.Minecraft;

public class YawAlign {

    public static float alignToAxis(Minecraft client) {

        assert client.player != null;
        float alignedYaw = Math.round(client.player.getYRot() / 90f) * 90f;
        client.player.setYRot(alignedYaw);

        return alignedYaw;
    }
}
