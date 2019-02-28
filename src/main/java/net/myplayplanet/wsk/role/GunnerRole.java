package net.myplayplanet.wsk.role;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class GunnerRole extends AbstractRole {

    public GunnerRole() {
        canEnter = false;
        canRedstone = false;
        canTnt = true;
    }

    @Override
    protected void setInventory() {
        addItem(0, new ItemStack(Material.WOODEN_SWORD));
        addItem(1, bow);
        addItem(2, arrows);
        addItem(3, wooden_pickaxe);
        addItem(4, concrete);
        addItem(5, concrete);
        addItem(6, concrete);
        addItem(7, tnt);
        addItem(8, tnt);
        addItem(9, tnt);
        addItem(10, tnt);
        addItem(11, tnt);
        addItem(12, tnt);
        addItem(13, tnt);
        addItem(14, tnt);
        addItem(15, tnt);
        addItem(16, tnt);
        addItem(17, tnt);
        addItem(18, tnt);
        addItem(19, tnt);
        addItem(20, tnt);
        addItem(21, tnt);
        addItem(22, tnt);
        addItem(23, tnt);
        addItem(24, tnt);
        addItem(25, tnt);
        addItem(26, tnt);
        addItem(27, tnt);
        addItem(28, tnt);
        addItem(29, tnt);
        addItem(30, tnt);
        addItem(31, tnt);
        addItem(32, tnt);
        addItem(33, tnt);
        addItem(34, tnt);
        addItem(35, tnt);

        addItem(38, new ItemStack(Material.CHAINMAIL_CHESTPLATE));
    }
}
