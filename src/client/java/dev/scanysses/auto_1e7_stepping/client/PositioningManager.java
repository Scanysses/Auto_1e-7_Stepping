package dev.scanysses.auto_1e7_stepping.client;

import dev.scanysses.auto_1e7_stepping.Auto1e7Config;
import dev.scanysses.auto_1e7_stepping.PositioningMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

public class PositioningManager {

    public PositioningManager(MinecraftClient client) {
    }

    public void executePositioning() {
        PositioningMode mode = Auto1e7Config.HANDLER.instance().positioningMode;

        switch (mode) {
            case PACKET:
                executeAxisPush();
                break;
            case SCRIPT:
                if (MinecraftClient.getInstance().player != null) {
                    MinecraftClient.getInstance().player.sendMessage(Text.literal("Script mode not available yet!"), false);
                }
                break;
        }
    }

    public void executeAxisPush() {
        MinecraftClient client1 = MinecraftClient.getInstance();

        ClientPlayerEntity player = client1.player;
        if (player == null) return;

        double x = client1.player.getX();
        double y = client1.player.getY();
        double z = client1.player.getZ();

//        double distance = 3.1623e-4;
        double distance = 0.00000005; // 0.0000001
//        double distance = -3.0;

        Vec3d offset;

//        float yaw = client1.player.getYaw() % 360;

        float yaw = YawAlign.alignToAxis(client1) % 360f;
        if (yaw < 0) yaw += 360f;


        YawAlign.alignToAxis(client1);

        offset = switch ((int) yaw) {
            case 0 -> new Vec3d(0, 0, -distance); // +Z
            case 90 -> new Vec3d(-distance, 0, 0); // -X
            case 180 -> new Vec3d(0, 0, distance);  // -Z
            case 270 -> new Vec3d(distance, 0, 0);  // +X
            default -> Vec3d.ZERO;
        };

        client1.player.updatePosition(x + offset.x, y, z + offset.z);

    }
}
