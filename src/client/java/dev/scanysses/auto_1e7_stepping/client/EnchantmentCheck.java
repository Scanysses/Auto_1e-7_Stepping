package dev.scanysses.auto_1e7_stepping.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;

public class EnchantmentCheck {

    private EnchantmentCheck() {}

    public static int getEnchantmentLevel(Minecraft client, ResourceKey<Enchantment> enchantmentKey, EquipmentSlot slot) {
        if (client == null || client.player == null || client.level == null) return 0;

        ItemStack stack = client.player.getItemBySlot(slot);
        if (stack.isEmpty()) return 0;

        var reg = client.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);

        Holder<Enchantment> enchantmentEntry = reg.getOrThrow(enchantmentKey);

        return EnchantmentHelper.getItemEnchantmentLevel(enchantmentEntry, stack);
    }
}
