package dev.scanysses.auto_1e7_stepping.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class Auto1e7ClientKeyBinds {
    private static KeyMapping positionKey;

    public static void register() {

        KeyMapping.Category category = KeyMapping.Category.register(Identifier.parse("bind.auto1e7"));

        positionKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
                "key.auto1e7.position",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(positionKey.consumeClick()) {
                PositioningManager manager = new PositioningManager(Minecraft.getInstance());
                manager.executePositioning();
            }
        });
    }
}
