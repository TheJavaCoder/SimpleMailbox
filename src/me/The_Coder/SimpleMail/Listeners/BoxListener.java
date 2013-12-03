
package me.The_Coder.SimpleMail.Listeners;

import java.util.Set;

import me.The_Coder.SimpleMail.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;


public class BoxListener implements Listener{

	
	main plugin;

    //contrustor to main class
	public BoxListener (main Main) {
		
		plugin = Main;
	}
	
	//To create a mailbox
		@EventHandler
		public void onCreation(BlockPlaceEvent e) {
			Player p = e.getPlayer();
			Block blockplace = e.getBlock();
			Block blockbelow = blockplace.getRelative(BlockFace.DOWN);
			
			Block blockup = blockplace.getRelative(BlockFace.UP);
			
			if(blockplace.getType() == Material.LOG && blockbelow.getType() == Material.FENCE && blockup.getType() == Material.AIR ) {
				Location loc = e.getBlock().getLocation();
					if(!plugin.getConfig().contains("MailBoxs." + p.getWorld().getName() + "." + p.getName())) {
						p.sendMessage("[MailBox]: " + ChatColor.GREEN + " You created a mailbox");
						plugin.getConfig().set("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".y", loc.getBlockY());
						plugin.getConfig().set("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".x", loc.getBlockX());
						plugin.getConfig().set("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".z", loc.getBlockZ());
						plugin.saveConfig();
					}else {
						
						p.sendMessage("[MailBox]: " + ChatColor.RED + "You have a mailbox in " + p.getWorld().getName() + "!");
						
					}
				}
		}
		
		
	//To handle the breaking of someones mailbox 
		@EventHandler
		public void mailboxbreak(BlockBreakEvent e) {
			Player p = e.getPlayer();
			Block blockbreak = e.getBlock();
			Block blockbelow = blockbreak.getRelative(BlockFace.DOWN);
			Block blockup = blockbreak.getRelative(BlockFace.UP);
			Location blockbroken = e.getBlock().getLocation();
			if(blockbreak.getType() == Material.LOG && blockbelow.getType() == Material.FENCE || blockbreak.getType() == Material.FENCE && blockup.getType() == Material.LOG) {
				if(blockbroken.getY() == plugin.getConfig().getInt("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".y") && blockbroken.getZ() == plugin.getConfig().getInt("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".z") && blockbroken.getX() == plugin.getConfig().getInt("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".x") ||
						blockbroken.getBlockY() == plugin.getConfig().getInt("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".y")-1 && blockbroken.getZ() == plugin.getConfig().getInt("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".z") - 1 && blockbroken.getX() == plugin.getConfig().getInt("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".x") - 1 ){
					p.sendMessage("[MailBox]: " + ChatColor.GREEN + "You deleted your mailbox!");
					plugin.getConfig().set("MailBoxs." + p.getWorld().getName() + "." + p.getName(), null);
					plugin.saveConfig();
				}else {
					if(plugin.getConfig().getBoolean("Settings.BoxBreak") == false) {
						ConfigurationSection sec = plugin.getConfig().getConfigurationSection(
								"MailBoxs." + p.getWorld().getName());
	
						Set<String> players = sec.getKeys(false);
	
						for (String pl : players) {
							Player play = Bukkit.getServer().getPlayer(pl);
							
							if (sec.getInt(play.getName() + ".y") == blockbroken.getY() && sec.getInt(play.getName() + ".x") == blockbroken.getX() && sec.getInt(play.getName() + ".z") == blockbroken.getZ() || sec.getInt(play.getName() + ".y") - 1 == blockbroken.getY()) {
									p.sendMessage("[MailBox]: " + ChatColor.RED + "You can't destroy that players mailbox");
									e.setCancelled(true);
							}
						}
					}else {
						
						p.sendMessage("[MailBox]: " + ChatColor.RED + "You broke someone's mailbox!");
						
						
						
					}
				}
			}
		}
}
