package dev.scanysses.auto_1e7_stepping.client;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;

import java.util.Queue;

public class ScriptPositioning {
    public void executeScriptMode(MinecraftClient client, Queue<PositioningStep> scriptQueue) {
        if (client.player == null) return;

        System.out.println("[Auto1e7] Positioning activated with SCRIPT mode");

        boolean sneakToggleMode = client.options.getSneakToggled().getValue();

        double yawChange = ((double) 100 / 15) * 0.05;

        int swiftSneakLevel = EnchantmentCheck.getEnchantmentLevel(client, Enchantments.SWIFT_SNEAK, EquipmentSlot.LEGS);

        boolean hasSwiftSneak = swiftSneakLevel > 0;

        boolean hasSoulSpeedBoost = false;

        if (EnchantmentCheck.getEnchantmentLevel(client, Enchantments.SOUL_SPEED, EquipmentSlot.FEET) > 0) {

            assert client.world != null;
            BlockPos blockPosDown = client.player.getBlockPos().down();
            BlockState blockStateDown = client.world.getBlockState(blockPosDown);

            BlockPos blockPosAtFeet = BlockPos.ofFloored(client.player.getX(), client.player.getY() - 0.01, client.player.getZ());
            BlockState blockStateAtFeet = client.world.getBlockState(blockPosAtFeet);

            boolean onSoulBlock = blockStateDown.isOf(Blocks.SOUL_SAND) ||
                    blockStateDown.isOf(Blocks.SOUL_SOIL) || blockStateAtFeet.isOf(Blocks.SOUL_SAND);

            if (onSoulBlock) {
                hasSoulSpeedBoost = true;
            }

        }

        boolean hasSpeed = client.player.hasStatusEffect(StatusEffects.SPEED);

        int tickDelay = switch ((hasSoulSpeedBoost ? 1 : 0) | (hasSwiftSneak ? 2 : 0) | (hasSpeed ? 4 : 0)) {
            case 7 -> { yawChange *= 0.5; yield 4; }    // speed effect + soul speed + swift sneak
            case 6 -> { yawChange *= 0.5; yield 3; }    // speed effect + swift sneak
            case 5 -> 3;                                // speed effect + soul speed
            case 4 -> 2;                                // speed effect
            case 3 -> { yawChange *= 0.5; yield 4; }    // soul speed + swift sneak
            case 2 -> 3;                                // swift sneak
            case 1 -> 3;                                // soul speed
            default -> 1;                               // no enchants or status effects
        };

        double finalYawChange = yawChange;

        YawAlign.alignToAxis(client);

        scriptQueue.clear();

        scriptQueue.add(new PositioningStep(0, () -> press(client.options.sneakKey, true)));
        if (!sneakToggleMode) {
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.backKey, true)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.backKey, false)));
            scriptQueue.add(new PositioningStep(tickDelay, () -> client.player.changeLookDirection(finalYawChange, 0)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.forwardKey, true)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.forwardKey, false)));
        } else {
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.sneakKey, false)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.backKey, true)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.backKey, false)));
            scriptQueue.add(new PositioningStep(tickDelay, () -> client.player.changeLookDirection(finalYawChange, 0)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.forwardKey, true)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.forwardKey, false)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.sneakKey, true)));
        }
        scriptQueue.add(new PositioningStep(0, () -> press(client.options.sneakKey, false)));

    }

    private void press(KeyBinding key, boolean pressed) {
        KeyBinding.setKeyPressed(key.getDefaultKey(), pressed);
    }
}
