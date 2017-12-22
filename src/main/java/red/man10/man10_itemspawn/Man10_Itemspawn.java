package red.man10.man10_itemspawn;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Man10_Itemspawn extends JavaPlugin implements CommandExecutor{

    public static CustomConfig location;

    public List<Location> spawnlist = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("mis").setExecutor(this);
        location = new CustomConfig(this, "location.yml");
        getServer().getPluginManager().registerEvents(new SpawnEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public  boolean  onCommand(CommandSender sender, Command command, String label, String[] args){
        if (!(sender instanceof Player)) return false;
        Player p = (Player)sender;

        switch (args.length){
            case 0:

                if (p.hasPermission("man10itemspawn.edit")) {
                    p.sendMessage("§l===§a§l/mis HELP§f§l===");
                    p.sendMessage("§elist §f: アイテムスポーン場所一覧表示");
                    p.sendMessage("§eadd §f: アイテムスポーン場所追加");
                    p.sendMessage("§eremove [番号] §f: [番号]のスポーン場所削除");
                    p.sendMessage("§ereset §f: スポーン場所全削除");
                    p.sendMessage("§e使用法 §f: チェストに看板を貼り付け1行目に'Man10ItemSpawn'と書きチェスト内に一個ずつアイテムを入れて、看板右クリックでスポーン");
                    return true;
                }else {
                    p.sendMessage("§4権限がありません");
                    return false;
                }

            case 1:

                if (args[0].equalsIgnoreCase("add")) {

                    if (p.hasPermission("man10itemspawn.edit")) {
                        Location spawnpoint = p.getLocation();

                        spawnlist.add(spawnpoint);

                        location.getConfig().set("Location", spawnlist);

                        location.saveConfig();

                        p.sendMessage("§aadd point Complete");
                        return true;
                    }else {
                        p.sendMessage("§4権限がありません");
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("reset")){

                    if (p.hasPermission("man10itemspawn.edit")) {
                        location.getConfig().getList("Location").clear();
                        location.saveConfig();

                        p.sendMessage("§aReset Complete");
                        return true;
                    }else {
                        p.sendMessage("§4権限がありません");
                        return false;
                    }

                }

                if (args[0].equalsIgnoreCase("list")){
                    if (p.hasPermission("man10itemspawn.edit")) {

                        p.sendMessage("§e§l<§b§lSpawn Location List§e§l>");

                        for (int i = 0; i < location.getConfig().getList("Location").size(); i++) {
                            Location spawnpoint = (Location) location.getConfig().getList("Location").get(i);

                            p.sendMessage("§e" + i + "§f§r: §aWorld§f:" + String.valueOf(spawnpoint.getWorld().getName()) + " / §aX§f:" + String.valueOf(Math.round(spawnpoint.getX())) + " / §aY§f:" + String.valueOf(Math.round(spawnpoint.getY())) + " / §aZ§f:" + String.valueOf(Math.round(spawnpoint.getZ())));

                        }

                        return true;
                    }
                }
                break;

            case 2:
                if (args[0].equalsIgnoreCase("remove")){
                    if (p.hasPermission("man10itemspawn.edit")){

                        int index;

                        try {
                            index = Integer.parseInt(args[1]);
                        }catch (NumberFormatException mis){
                            sender.sendMessage("§4§l番号を指定してください.");
                            return false;
                        }

                        location.getConfig().getList("Location").remove(index);
                        location.saveConfig();
                        p.sendMessage("§aRemove Complete");

                        return true;

                    }
                }
                break;

        }
        return true;
    }
}
