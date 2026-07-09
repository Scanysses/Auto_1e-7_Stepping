package dev.scanysses.auto_1e7_stepping.client;

import dev.scanysses.auto_1e7_stepping.Auto1e7Config;
import net.minecraft.client.gui.screens.Screen;

public class Auto1e7ConfigScreen {

    public static Screen createScreen(Screen parent) {
        return Auto1e7Config.HANDLER.generateGui().generateScreen(parent);
    }

}
