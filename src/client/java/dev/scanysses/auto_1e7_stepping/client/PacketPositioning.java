package dev.scanysses.auto_1e7_stepping.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;

public class PacketPositioning {
    public void executeAxisPush(MinecraftClient client) {

        System.out.println("[Auto1e7] Positioning activated with PACKET mode");

        if (client.player == null) return;

        double x = client.player.getX();
        double y = client.player.getY();
        double z = client.player.getZ();

        double distance = 0.00000005; // 0.0000001
//        double distance = -3.0;

        Vec3d offset;

        float yaw = YawAlign.alignToAxis(client) % 360f;
        if (yaw < 0) yaw += 360f;


        YawAlign.alignToAxis(client);

        offset = switch ((int) yaw) {
            case 0 -> new Vec3d(0, 0, -distance); // +Z
            case 90 -> new Vec3d(-distance, 0, 0); // -X
            case 180 -> new Vec3d(0, 0, distance);  // -Z
            case 270 -> new Vec3d(distance, 0, 0);  // +X
            default -> Vec3d.ZERO;
        };

        client.player.updatePosition(x + offset.x, y, z + offset.z);

    }
}
