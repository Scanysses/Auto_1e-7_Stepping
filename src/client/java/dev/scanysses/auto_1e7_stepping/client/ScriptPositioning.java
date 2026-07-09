package dev.scanysses.auto_1e7_stepping.client;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;

import java.util.Queue;

public class ScriptPositioning {
    public void executeScriptMode(Minecraft client, Queue<PositioningStep> scriptQueue) {
        if (client.player == null) return;

        System.out.println("[Auto1e7] Positioning activated with SCRIPT mode");

        boolean sneakToggleMode = client.options.toggleCrouch().get();

        double yawChange = ((double) 100 / 15) * 0.05;

        int swiftSneakLevel = EnchantmentCheck.getEnchantmentLevel(client, Enchantments.SWIFT_SNEAK, EquipmentSlot.LEGS);

        boolean hasSwiftSneak = swiftSneakLevel > 0;

        boolean hasSoulSpeedBoost = false;

        if (EnchantmentCheck.getEnchantmentLevel(client, Enchantments.SOUL_SPEED, EquipmentSlot.FEET) > 0) {

            assert client.level != null;
            BlockPos blockPosDown = client.player.blockPosition().below();
            BlockState blockStateDown = client.level.getBlockState(blockPosDown);

            BlockPos blockPosAtFeet = BlockPos.containing(client.player.getX(), client.player.getY() - 0.01, client.player.getZ());
            BlockState blockStateAtFeet = client.level.getBlockState(blockPosAtFeet);

            boolean onSoulBlock = blockStateDown.is(Blocks.SOUL_SAND) ||
                    blockStateDown.is(Blocks.SOUL_SOIL) || blockStateAtFeet.is(Blocks.SOUL_SAND);

            if (onSoulBlock) {
                hasSoulSpeedBoost = true;
            }

        }

        boolean hasSpeed = client.player.hasEffect(MobEffects.SPEED);

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

        scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyShift, true)));
        if (!sneakToggleMode) {
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyDown, true)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyDown, false)));
            scriptQueue.add(new PositioningStep(tickDelay, () -> client.player.turn(finalYawChange, 0)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyUp, true)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyUp, false)));
        } else {
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyShift, false)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyDown, true)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyDown, false)));
            scriptQueue.add(new PositioningStep(tickDelay, () -> client.player.turn(finalYawChange, 0)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyUp, true)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyUp, false)));
            scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyShift, true)));
        }
        scriptQueue.add(new PositioningStep(0, () -> press(client.options.keyShift, false)));

    }

    private void press(KeyMapping key, boolean pressed) {
        KeyMapping.set(key.getDefaultKey(), pressed);
    }
}
