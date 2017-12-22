package red.man10.man10_itemspawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static red.man10.man10_itemspawn.Man10_Itemspawn.location;

public class SpawnEvent implements Listener {

    @EventHandler
    public void onPlaceSign(SignChangeEvent e){

        Player p = e.getPlayer();

        if (p.hasPermission("man10itemspawn.use")){

            if (e.getLine(0).equals("Man10ItemSpawn")) {

                e.setLine(0, "§b======================");
                e.setLine(1, "§d§lMa§f§ln§a§l10§e§lItemSpawn");
                e.setLine(3, "§b======================");


            }

        }

    }

    @EventHandler
    public void onClickSign(PlayerInteractEvent e) {

        Player p = e.getPlayer();

        if (!e.getClickedBlock().getType().equals(Material.WALL_SIGN)) {
            return;
        }

        if (p.hasPermission("man10itemspawn.use")) {

            Sign sign = (Sign) e.getClickedBlock().getState();

            if (sign.getLine(1).equals("§d§lMa§f§ln§a§l10§e§lItemSpawn")){

                Block b = e.getClickedBlock().getRelative(e.getBlockFace().getOppositeFace());

                if (b.getType() == Material.CHEST) {


                    Chest ch = (Chest) b.getState();

                    Inventory chinv = ch.getInventory();

                    int inventory = chinv.firstEmpty();

                    ItemStack[] spawnitem = new ItemStack[inventory];

                    for (int i = 0; i < spawnitem.length; i++) {

                        spawnitem[i] = chinv.getItem(i);

                        if (spawnitem[i] == null) {
                            continue;
                        }

                    }

                    for (int i = 0; i < location.getConfig().getList("Location").size(); i++) {

                        Random r = new Random();

                        if (spawnitem.length == 0){
                            p.sendMessage("§4§lアイテムがありません");
                            return;
                        }else {
                            int rint = r.nextInt(spawnitem.length);
                            p.getWorld().dropItemNaturally((Location) location.getConfig().getList("Location").get(i), spawnitem[rint]);
                        }
                    }

                }
            }
        }


    }
}
