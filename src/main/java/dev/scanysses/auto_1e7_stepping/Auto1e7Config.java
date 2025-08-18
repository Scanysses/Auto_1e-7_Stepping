package dev.scanysses.auto_1e7_stepping;

import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier; // or ResourceLocation depending on mappings

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.CustomDescription;

public final class Auto1e7Config {

    public static final ConfigClassHandler<Auto1e7Config> HANDLER = ConfigClassHandler.createBuilder(Auto1e7Config.class)
            .id(Identifier.of("auto1e7", "config"))
            .serializer(cfg -> GsonConfigSerializerBuilder.create(cfg)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("auto1e7.json5"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = "Player positioning mode")
    @AutoGen(category = "general")
    @EnumCycler
    @CustomDescription ("packetMode.description")
    public PositioningMode positioningMode = PositioningMode.PACKET;

//    public static Auto1e7Config instance() {
//        return HANDLER.instance();
//    }

    public static void load() {
        Auto1e7Config.HANDLER.load();
    }

//    public static void save() {
//        Auto1e7Config.HANDLER.save();
//    }
}
