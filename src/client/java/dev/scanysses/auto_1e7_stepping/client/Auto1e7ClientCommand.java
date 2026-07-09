package dev.scanysses.auto_1e7_stepping.client;

import dev.scanysses.auto_1e7_stepping.Auto1e7Config;
import net.fabricmc.fabric.api.client.command.v2.ClientCommands;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.client.Minecraft;

public class Auto1e7ClientCommand {

    public static void register() {

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, commandRegistryAccess) ->
                dispatcher.register(ClientCommands.literal("auto1e7")
                .then(ClientCommands.literal("config")
                        .executes(context -> {
                            Minecraft client = context.getSource().getClient();
                            client.schedule(() -> client.execute(() ->
                                    client.gui.setScreen(Auto1e7Config.HANDLER.generateGui().generateScreen(null))));
                            return 1;
                        })
                ).then(ClientCommands.literal("positioning")
                        .executes(context -> {
                            PositioningManager manager = new PositioningManager(context.getSource().getClient());
                            manager.executePositioning();
                            return 1;
                        })
                )
        ));

    }

}
