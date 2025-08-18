package dev.scanysses.auto_1e7_stepping.client;

import dev.scanysses.auto_1e7_stepping.Auto1e7Config;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;

public class Auto1e7ClientCommand {

    public static void register() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, commandRegistryAccess) ->
                dispatcher.register(ClientCommandManager.literal("auto1e7")
                .then(ClientCommandManager.literal("config")
                        .executes(context -> {
                            MinecraftClient client = context.getSource().getClient();
                            client.send(() -> client.execute(() ->
                                    client.setScreen(Auto1e7Config.HANDLER.generateGui().generateScreen(null))));
                            return 1;
                        })
                ).then(ClientCommandManager.literal("positioning")
                        .executes(context -> {
                            PositioningManager manager = new PositioningManager(context.getSource().getClient());
                            manager.executePositioning();
                            return 1;
                        })
                )
        ));

    }

}
