package net.myplayplanet.wsk.role;

import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import net.myplayplanet.wsk.util.ColorConverter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

public abstract class AbstractRole {

    /*
    Help for indexing:
    0-8: Hotbar, from left to right
    36-39: Armor, from boots to helmet
    9-17: Top row, from left to right
    18-26: Mid row, from left to right
    27-35: Bottom row, from left to right
     */
    protected HashMap<Integer, ItemStack> invItems = new HashMap<>();

    // Permanent effects a player has
    protected HashSet<PotionEffect> permanentEffects = new HashSet<>();

    protected abstract void setInventory();

    public void setItems(Player p) {
        ForkJoinPool.commonPool().execute(() -> {
            Team team = WSKPlayer.getPlayer(p).getTeam();

            p.getInventory().clear();

            invItems.entrySet().forEach(e -> {
                ItemMeta im = e.getValue().getItemMeta();
                im.setUnbreakable(true);
                e.getValue().setItemMeta(im);

                if (e.getValue() == concrete)
                    e.setValue(new ItemStack(ColorConverter.getConcreteFromColorCode(team.getProperties().getColorCode()), 64));

                p.getInventory().setItem(e.getKey(), e.getValue());
            });

            p.getInventory().setHeldItemSlot(0);
        });
    }

    protected void addItem(int i, ItemStack is) {
        invItems.put(i, is);
    }

    protected AbstractRole() {
        setInventory();

        ItemMeta im = iron_pickaxe.getItemMeta();
        im.setUnbreakable(true);
        iron_pickaxe.setItemMeta(im);

        im = stone_pickaxe.getItemMeta();
        im.setUnbreakable(true);
        stone_pickaxe.setItemMeta(im);

        im = wooden_pickaxe.getItemMeta();
        im.setUnbreakable(true);
        wooden_pickaxe.setItemMeta(im);
    }

    protected ItemStack bow = new ItemStack(Material.BOW);
    protected ItemStack stone_pickaxe = new ItemStack(Material.STONE_PICKAXE);
    protected ItemStack iron_pickaxe = new ItemStack(Material.IRON_PICKAXE);
    protected ItemStack wooden_pickaxe = new ItemStack(Material.WOODEN_PICKAXE);
    protected ItemStack piston = new ItemStack(Material.PISTON, 64);
    protected ItemStack stickyPiston = new ItemStack(Material.STICKY_PISTON, 64);
    protected ItemStack redstone = new ItemStack(Material.REDSTONE, 64);
    protected ItemStack redstoneTorch = new ItemStack(Material.REDSTONE_TORCH, 64);
    protected ItemStack redstoneRepeater = new ItemStack(Material.REPEATER, 64);
    protected ItemStack redstoneComparator = new ItemStack(Material.COMPARATOR, 64);
    protected ItemStack wooden_plate = new ItemStack(Material.OAK_PRESSURE_PLATE, 64);
    protected ItemStack wooden_button = new ItemStack(Material.OAK_BUTTON, 64);
    protected ItemStack slime = new ItemStack(Material.SLIME_BLOCK, 64);
    protected ItemStack redstoneBlock = new ItemStack(Material.REDSTONE_BLOCK, 64);
    protected ItemStack observer = new ItemStack(Material.OBSERVER, 64);
    protected ItemStack lever = new ItemStack(Material.LEVER, 64);

    // New redstone stuff
    protected ItemStack strings = new ItemStack(Material.STRING, 64);
    protected ItemStack lamps = new ItemStack(Material.REDSTONE_LAMP, 64);
    protected ItemStack dropper = new ItemStack(Material.DROPPER, 64);
    protected ItemStack trapdoor = new ItemStack(Material.IRON_TRAPDOOR, 64);

    protected ItemStack concrete = new ItemStack(Material.WHITE_CONCRETE, 64);
    protected ItemStack tnt = new ItemStack(Material.TNT, 64);
    protected ItemStack air = new ItemStack(Material.AIR, 1);
    protected ItemStack arrows = new ItemStack(Material.ARROW, 16);
}
