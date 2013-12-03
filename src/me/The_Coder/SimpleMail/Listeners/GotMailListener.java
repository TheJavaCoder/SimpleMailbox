<<<<<<< HEAD
package me.The_Coder.SimpleMail.Listeners;

import java.util.List;

import me.The_Coder.SimpleMail.main;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GotMailListener implements Listener {

	 main plugin;
	
	
	public GotMailListener(main Main) {
		
	plugin = Main;	
		
	}
	
	
	@EventHandler 
	public void onJoinEvent(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if(plugin.getConfig().contains("MailBoxs." + e.getPlayer().getWorld().getName()+ "." + e.getPlayer().getName())) {
			if(plugin.getConfig().getList("MailBoxs." + player.getWorld().getName() + "." + player.getName() + ".books") != null) {
				List<?> books = plugin.getConfig().getList("MailBoxs." + player.getWorld().getName() + "." + player.getName() + ".books");
				if(books.size() == 1) {
					
					player.sendMessage("[MailBox]: " + ChatColor.AQUA + "You have one letter in '" + player.getWorld().getName() + "'.");
					
				}else if(books.size() > 1) {
					
					player.sendMessage("[MailBox]: " + ChatColor.AQUA + "You have " + books.size() + " letters in '" + player.getWorld().getName() + "'.");
					
				}
				
				
			}
		}
		
	}
	
	
}
=======
package me.The_Coder.SimpleMail.Listeners;

import java.util.List;

import me.The_Coder.SimpleMail.main;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GotMailListener implements Listener {

	 main plugin;
	
	
	public GotMailListener(main Main) {
		
	plugin = Main;	
		
	}
	
	
	@EventHandler 
	public void onJoinEvent(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if(plugin.getConfig().contains("MailBoxs." + e.getPlayer().getWorld().getName()+ "." + e.getPlayer().getName())) {
			if(plugin.getConfig().getList("MailBoxs." + player.getWorld().getName() + "." + player.getName() + ".books") != null) {
				List<?> books = plugin.getConfig().getList("MailBoxs." + player.getWorld().getName() + "." + player.getName() + ".books");
				if(books.size() == 1) {
					
					player.sendMessage("[MailBox]: " + ChatColor.AQUA + "You have one letter in '" + player.getWorld().getName() + "'.");
					
				}else if(books.size() > 1) {
					
					player.sendMessage("[MailBox]: " + ChatColor.AQUA + "You have " + books.size() + " letters in '" + player.getWorld().getName() + "'.");
					
				}
				
				
			}
		}
		
	}
	
	
}
>>>>>>> 49cafe4ff463937bb0ff81e2e1d5c6ce189c4fb0
