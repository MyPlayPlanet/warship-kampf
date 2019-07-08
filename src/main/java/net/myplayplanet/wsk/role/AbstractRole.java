package net.myplayplanet.wsk.role;

import lombok.Getter;
import lombok.Setter;
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

@Getter
public abstract class AbstractRole {

    protected final ItemStack bow = new ItemStack(Material.BOW);
    protected final ItemStack stone_pickaxe = new ItemStack(Material.STONE_PICKAXE);
    protected final ItemStack iron_pickaxe = new ItemStack(Material.IRON_PICKAXE);
    protected final ItemStack wooden_pickaxe = new ItemStack(Material.WOODEN_PICKAXE);
    protected final ItemStack piston = new ItemStack(Material.PISTON, 64);
    protected final ItemStack stickyPiston = new ItemStack(Material.STICKY_PISTON, 64);
    protected final ItemStack redstone = new ItemStack(Material.REDSTONE, 64);
    protected final ItemStack redstoneTorch = new ItemStack(Material.REDSTONE_TORCH, 64);
    protected final ItemStack redstoneRepeater = new ItemStack(Material.REPEATER, 64);
    protected final ItemStack redstoneComparator = new ItemStack(Material.COMPARATOR, 64);
    protected final ItemStack wooden_plate = new ItemStack(Material.OAK_PRESSURE_PLATE, 64);
    protected final ItemStack wooden_button = new ItemStack(Material.OAK_BUTTON, 64);
    protected final ItemStack slime = new ItemStack(Material.SLIME_BLOCK, 64);
    protected final ItemStack redstoneBlock = new ItemStack(Material.REDSTONE_BLOCK, 64);
    protected final ItemStack observer = new ItemStack(Material.OBSERVER, 64);
    protected final ItemStack lever = new ItemStack(Material.LEVER, 64);

    // New redstone stuff
    protected final ItemStack strings = new ItemStack(Material.STRING, 64);
    protected final ItemStack lamps = new ItemStack(Material.REDSTONE_LAMP, 64);
    protected final ItemStack dropper = new ItemStack(Material.DROPPER, 64);
    protected final ItemStack trapdoor = new ItemStack(Material.IRON_TRAPDOOR, 64);

    protected final ItemStack concrete = new ItemStack(Material.WHITE_CONCRETE, 64);
    protected final ItemStack tnt = new ItemStack(Material.TNT, 64);
    protected final ItemStack air = new ItemStack(Material.AIR, 1);
    protected final ItemStack arrows = new ItemStack(Material.ARROW, 16);

    /*
    Help for indexing:
    0-8: Hotbar, from left to right
    36-39: Armor, from boots to helmet
    9-17: Top row, from left to right
    18-26: Mid row, from left to right
    27-35: Bottom row, from left to right
     */
    @Setter
    protected HashMap<Integer, ItemStack> invItems = new HashMap<>();

    // Permanent effects a player has
    @Setter
    protected HashSet<PotionEffect> permanentEffects = new HashSet<>();

    @Setter
    protected boolean canEnter = false;
    @Setter
    protected boolean canTnt = false;
    @Setter
    protected boolean canRedstone = false;
    @Setter
    protected boolean canEnterAtAll = true;

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
}
