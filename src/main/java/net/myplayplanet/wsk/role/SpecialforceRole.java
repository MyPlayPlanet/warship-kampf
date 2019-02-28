package net.myplayplanet.wsk.role;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpecialforceRole extends AbstractRole {

    public SpecialforceRole() {
        canEnter = true;
        canRedstone = false;
        canTnt = false;
    }

    @Override
    protected void setInventory() {
        addItem(0, new ItemStack(Material.STONE_SWORD));
        addItem(1, bow);
        addItem(2, stone_pickaxe);

        ItemStack is = new ItemStack(Material.POTION);
        PotionMeta pm = (PotionMeta) is.getItemMeta();
        PotionEffect pe = new PotionEffect(PotionEffectType.WATER_BREATHING, 3600, 1);
        pm.setDisplayName("§9Unterwasseratmung");
        pm.setColor(Color.BLUE);
        pm.addCustomEffect(pe, true);
        is.setItemMeta(pm);
        addItem(3, is);

        addItem(4, concrete);
        addItem(5, concrete);
        addItem(6, concrete);
        addItem(7, concrete);

        addItem(35, arrows);

        is = new ItemStack(Material.LEATHER_BOOTS);
        is.addEnchantment(Enchantment.DEPTH_STRIDER, 1);
        addItem(36, is);
        addItem(37, new ItemStack(Material.LEATHER_LEGGINGS));
        addItem(38, new ItemStack(Material.CHAINMAIL_CHESTPLATE));
        addItem(39, new ItemStack(Material.LEATHER_HELMET));
    }
}
