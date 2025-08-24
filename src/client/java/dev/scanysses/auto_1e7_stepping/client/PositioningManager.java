package dev.scanysses.auto_1e7_stepping.client;

import dev.scanysses.auto_1e7_stepping.Auto1e7Config;
import dev.scanysses.auto_1e7_stepping.PositioningMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.LinkedList;
import java.util.Queue;

public class PositioningManager {

    private final MinecraftClient client;
    private static final Queue<Runnable> scriptQueue = new LinkedList<>();

    public PositioningManager(MinecraftClient client) {
        this.client = client;
    }

    public void executePositioning() {
        PositioningMode mode = Auto1e7Config.HANDLER.instance().positioningMode;

        switch (mode) {
            case PACKET -> executeAxisPush();
            case SCRIPT -> executeScriptMode();
        }
    }

    public void executeAxisPush() {
        MinecraftClient client1 = MinecraftClient.getInstance();

        System.out.println("[Auto1e7] Positioning activated with PACKET mode");

        ClientPlayerEntity player = client1.player;
        if (player == null) return;

        double x = client1.player.getX();
        double y = client1.player.getY();
        double z = client1.player.getZ();

        double distance = 0.00000005; // 0.0000001
//        double distance = -3.0;

        Vec3d offset;

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

    private void executeScriptMode() {
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        System.out.println("[Auto1e7] Positioning activated with SCRIPT mode");

//        if (client.player.getEquippedStack(EquipmentSlot.LEGS).getEnchantments().getLevel())

        if (!hasEnchantment(client, Enchantments.SWIFT_SNEAK, EquipmentSlot.LEGS)) {

            YawAlign.alignToAxis(client);

            scriptQueue.clear();

            if (!client.options.getSneakToggled().getValue()) {
                scriptQueue.add(() -> press(client.options.sneakKey, true));
                scriptQueue.add(() -> press(client.options.backKey, true));
                scriptQueue.add(() -> press(client.options.backKey, false));
                scriptQueue.add(() -> client.player.changeLookDirection(((double) 100 / 15) * 0.1, 0));
                scriptQueue.add(() -> {}); // very important one tick delay
                scriptQueue.add(() -> press(client.options.forwardKey, true));
                scriptQueue.add(() -> press(client.options.forwardKey, false));
                scriptQueue.add(() -> press(client.options.sneakKey, false));
            } else {
                scriptQueue.add(() -> press(client.options.sneakKey, true));
                scriptQueue.add(() -> press(client.options.sneakKey, false));
                scriptQueue.add(() -> press(client.options.backKey, true));
                scriptQueue.add(() -> press(client.options.backKey, false));
                scriptQueue.add(() -> client.player.changeLookDirection(((double) 100 / 15) * 0.1, 0));
                scriptQueue.add(() -> {}); // very important one tick delay
                scriptQueue.add(() -> press(client.options.forwardKey, true));
                scriptQueue.add(() -> press(client.options.forwardKey, false));
                scriptQueue.add(() -> press(client.options.sneakKey, true));
                scriptQueue.add(() -> press(client.options.sneakKey, false));
            }

        } else {
            client.player.sendMessage(Text.translatable("auto1e7.swiftsneak.exception.legs"), false);
        }

    }

    public void tick() {
        if (!scriptQueue.isEmpty()) {
            Runnable step = scriptQueue.poll();
            step.run();
        }
    }

    private void press(KeyBinding key, boolean pressed) {
        KeyBinding.setKeyPressed(key.getDefaultKey(), pressed);
    }

    public static boolean hasEnchantment(MinecraftClient client, RegistryKey<Enchantment> enchantments, EquipmentSlot slot) {
        assert client.player != null;
        ItemStack stack = client.player.getEquippedStack(slot);

        Registry<Enchantment> enchantmentRegistry = client.player.getWorld().getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);

        RegistryEntry<Enchantment> enchantmentEntry = enchantmentRegistry.getOrThrow(enchantments);

        if (stack.isEmpty()) {return false;}

        int level = EnchantmentHelper.getLevel(enchantmentEntry, stack);
        return level > 0;
    }

}
