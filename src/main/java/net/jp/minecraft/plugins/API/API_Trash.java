package net.jp.minecraft.plugins.API;

import net.jp.minecraft.plugins.Permissions;
import net.jp.minecraft.plugins.Utility.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

/**
 * TeisyokuPlugin2
 *
 * @author syokkendesuyo
 */
public class API_Trash implements Listener {
    public static void open(CommandSender sender) {
        if (!(sender instanceof Player)) {
            Msg.warning(sender, "ゴミ箱コマンドはゲーム内からのみ実行できます");
        }

        Player player = (Player) sender;

        if (!(player.hasPermission(Permissions.getGomibakoPermisson()))) {
            Msg.noPermissionMessage(sender, Permissions.getGomibakoPermisson());
            return;
        }

        ItemStack item0 = new ItemStack(Material.BOOK);
        ItemMeta itemmeta0 = item0.getItemMeta();
        itemmeta0.setDisplayName(ChatColor.GOLD + "ゴミ箱の使い方");
        itemmeta0.setLore(Arrays.asList(ChatColor.WHITE + "ゴミ箱に不要なアイテムを収納し、", ChatColor.WHITE + "画面を閉じると処分が完了します。"));
        item0.setItemMeta(itemmeta0);

        ItemStack item1 = new ItemStack(Material.RED_BED);
        ItemMeta itemmeta1 = item1.getItemMeta();
        itemmeta1.setDisplayName(ChatColor.GOLD + "画面を閉じる");
        itemmeta1.setLore(Arrays.asList(ChatColor.GRAY + "定食サーバオリジナルプラグイン！"));
        item1.setItemMeta(itemmeta1);

        Inventory inv = Bukkit.createInventory(player, 36, " ゴミ箱 ");
        inv.setItem(0, item0);
        inv.setItem(1, item1);
        player.openInventory(inv);
    }
}
