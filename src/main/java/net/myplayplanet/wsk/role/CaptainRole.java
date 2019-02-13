package net.myplayplanet.wsk.role;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class CaptainRole extends AbstractRole {

    @Override
    protected void setInventory() {
        ItemStack mainWeapon = new ItemStack(Material.GOLDEN_SWORD);
        mainWeapon.addEnchantment(Enchantment.DAMAGE_ALL, 1);

        addItem(0, mainWeapon);
        addItem(1, bow);
        addItem(2, iron_pickaxe);
        ItemStack is = new ItemStack(Material.POTION);
        PotionMeta pm = (PotionMeta) is.getItemMeta();
        pm.setDisplayName("§9Unterwasseratmung");
        pm.setColor(Color.BLUE);
        PotionEffect pe = new PotionEffect(PotionEffectType.WATER_BREATHING, 3600, 0);
        pm.addCustomEffect(pe, true);
        is.setItemMeta(pm);
        addItem(3, is);
        addItem(4, tnt);
        addItem(5, tnt);
        addItem(6, tnt);
        addItem(7, concrete);
        addItem(8, concrete);
        // Obere Reihe
        addItem(9, tnt);
        addItem(10, tnt);
        addItem(11, tnt);
        addItem(12, tnt);
        addItem(13, tnt);
        addItem(14, tnt);
        addItem(15, tnt);
        addItem(16, tnt);
        addItem(17, arrows);
        // Mittlere Reihe
        addItem(18, slime);
        addItem(19, stickyPiston);
        addItem(20, piston);
        addItem(21, tnt);
        addItem(22, tnt);
        addItem(23, tnt);
        addItem(24, tnt);
        addItem(25, tnt);
        addItem(26, tnt);
        // Unterste Reihe
        addItem(27, observer);
        addItem(28, redstoneBlock);
        addItem(29, redstoneTorch);
        addItem(30, redstoneComparator);
        addItem(31, redstoneRepeater);
        addItem(32, redstone);
        addItem(33, lever);
        addItem(34, wooden_plate);
        addItem(35, wooden_button);
    }
}
