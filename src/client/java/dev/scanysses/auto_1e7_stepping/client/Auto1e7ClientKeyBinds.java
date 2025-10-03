package dev.scanysses.auto_1e7_stepping.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class Auto1e7ClientKeyBinds {
    private static KeyBinding positionKey;

    public static void register() {

        KeyBinding.Category category = KeyBinding.Category.create(Identifier.of("bind.auto1e7"));

        positionKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.auto1e7.position",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O,
                category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while(positionKey.wasPressed()) {
                PositioningManager manager = new PositioningManager(MinecraftClient.getInstance());
                manager.executePositioning();
            }
        });
    }
}
