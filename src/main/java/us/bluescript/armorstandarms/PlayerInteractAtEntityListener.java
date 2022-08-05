package us.bluescript.armorstandarms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class PlayerInteractAtEntityListener implements Listener {
    private final Plugin plugin;

    public PlayerInteractAtEntityListener(Plugin pl) {
        plugin = pl;
    }

    @EventHandler
    public void onPlayerInteractAtEntity(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();
        Entity target = e.getRightClicked();

        if (target instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand)target;
            EntityEquipment equipment = p.getEquipment();
            if (equipment == null) return;

            ItemStack mainHandItem = equipment.getItemInMainHand();
            ItemStack offHandItem = equipment.getItemInOffHand();

            boolean isHoldingSticks;
            EquipmentSlot sticksHand = null;
            isHoldingSticks = mainHandItem.getType() == Material.STICK && mainHandItem.getAmount() >= 2;
            if (isHoldingSticks) {
                sticksHand = EquipmentSlot.HAND;
            } else {
                isHoldingSticks = offHandItem.getType() == Material.STICK && offHandItem.getAmount() >= 2;
                if (isHoldingSticks) {
                    sticksHand = EquipmentSlot.OFF_HAND;
                }
            }

            if (isHoldingSticks && !armorStand.hasArms()) {
                ItemStack stickItem = p.getEquipment().getItem(sticksHand);
                stickItem.setAmount(stickItem.getAmount() - 2);
                armorStand.setArms(true);
                e.setCancelled(true);
                return;
            }

            boolean isHoldingAxe;
            isHoldingAxe = mainHandItem.getType().toString().endsWith("_AXE");
            if (!isHoldingAxe) {
                isHoldingAxe = offHandItem.getType().toString().endsWith("_AXE");
            }

            if (isHoldingAxe && armorStand.hasArms() && p.isSneaking()) {
                World w = armorStand.getWorld();
                Location loc = armorStand.getLocation();
                w.dropItemNaturally(loc, new ItemStack(Material.STICK, 2));
                armorStand.setArms(false);
                e.setCancelled(true);
                return;
            }

            boolean isHoldingSlab;
            EquipmentSlot slabHand = null;
            isHoldingSlab = mainHandItem.getType() == Material.SMOOTH_STONE_SLAB;
            if (isHoldingSlab) {
                slabHand = EquipmentSlot.HAND;
            } else {
                isHoldingSlab = offHandItem.getType() == Material.SMOOTH_STONE_SLAB;
                if (isHoldingSlab) {
                    slabHand = EquipmentSlot.OFF_HAND;
                }
            }

            if (isHoldingSlab && !armorStand.hasBasePlate()) {
                ItemStack stickItem = p.getEquipment().getItem(slabHand);
                stickItem.setAmount(stickItem.getAmount() - 1);
                armorStand.setBasePlate(true);
                e.setCancelled(true);
                return;
            }

            boolean isHoldingPickaxe;
            isHoldingPickaxe = mainHandItem.getType().toString().endsWith("PICKAXE");
            if (!isHoldingPickaxe) {
                isHoldingPickaxe = offHandItem.getType().toString().endsWith("PICKAXE");
            }

            if (isHoldingPickaxe && armorStand.hasBasePlate() && p.isSneaking()) {
                World w = armorStand.getWorld();
                Location loc = armorStand.getLocation();
                w.dropItemNaturally(loc, new ItemStack(Material.SMOOTH_STONE_SLAB, 1));
                armorStand.setBasePlate(false);
                e.setCancelled(true);
            }
        }
    }
}
