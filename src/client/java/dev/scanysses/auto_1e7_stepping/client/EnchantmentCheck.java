package dev.scanysses.auto_1e7_stepping.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;

public class EnchantmentCheck {

    private EnchantmentCheck() {}

    public static int getEnchantmentLevel(MinecraftClient client, RegistryKey<Enchantment> enchantmentKey, EquipmentSlot slot) {
        if (client == null || client.player == null || client.world == null) return 0;

        ItemStack stack = client.player.getEquippedStack(slot);
        if (stack.isEmpty()) return 0;

        var reg = client.world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT);

        RegistryEntry<Enchantment> enchantmentEntry = reg.getOrThrow(enchantmentKey);

        return EnchantmentHelper.getLevel(enchantmentEntry, stack);
    }
}
