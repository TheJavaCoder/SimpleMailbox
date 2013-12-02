package me.The_Coder.SimpleMail.Listeners;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.The_Coder.SimpleMail.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.libs.jline.internal.InputStreamReader;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;


public class MailListener implements Listener {

	main plugin;

	public MailListener(main Main) {

		plugin = Main;

	}

	// To get the mail
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMessageListener(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		if(e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();
			Block blockbelow = block.getRelative(BlockFace.DOWN);
			//Location loc = e.getClickedBlock().getLocation();
			if(block.getType().equals(Material.LOG) && blockbelow.getType().equals(Material.FENCE)) {
				if(block.getLocation().getBlockY() == plugin.getConfig().getInt("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".y") && block.getLocation().getBlockX() == plugin.getConfig().getInt("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".x") && block.getLocation().getBlockZ() == plugin.getConfig().getInt("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".z")){
					if(plugin.getConfig().contains("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".books")) {
						List<?> books = plugin.getConfig().getList("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".books");

						
						for(Object names : books){
							// The name of the file to open.
					        

					        // This will reference one line at a time
							ItemStack is = new ItemStack(Material.WRITTEN_BOOK);
							BookMeta bm = (BookMeta) is.getItemMeta();
							
							
							plugin.getConfig().set("MailBoxs." + p.getWorld().getName() + "." + p.getName() + ".books", null);
							plugin.saveConfig();

					        try {
					            // FileReader reads text files in the default encoding.
					            File file = new File(plugin.getDataFolder(), names.toString() + ".txt");
					            FileInputStream fstream = new FileInputStream(file);
					            DataInputStream in = new DataInputStream(fstream);
					            BufferedReader br = new BufferedReader(new InputStreamReader(in));
					            
					            String strLine;
					            
					            List<String> bookstuff = new ArrayList<String>();
					            while ((strLine = br.readLine()) != null) {
					            	String[] splitstring = strLine.split(":");
					            	String part1 = splitstring[0];
					            	String part2 = splitstring[1];
					            	
					            	if(part1.equalsIgnoreCase("The title of this book is")) {
					            		
					            		
					            		
					            	}else if(part1.equalsIgnoreCase("It was written by")) {
					            		
					            		bm.setAuthor(part2);
					            		
					            	}else if(part1.contains("Page")) {
					            		String linebreak = part2.replace("   ",  "\n");
					            		
					            		bookstuff.add(linebreak);
					            		
					            		bm.setPages(bookstuff);
					            		
//					            		bm.addPage(part2);
					            		
					            	}
					            	
					            	
					                
					            }
					            
					            
					            br.close();
					            in.close();	
					            file.delete();
					        }
					        
					        catch(Exception ex) {
					            			
					        }
					        
					        bm.setTitle(names.toString());

					        is.setItemMeta(bm);
					        plugin.getServer().createInventory(null, 9, p.getName() + "'s Mailbox");
					        p.getInventory().addItem(is);
					        p.updateInventory();
							
							
						}
					}else{
						p.sendMessage("[MailBox]: " + ChatColor.GREEN + "You don't have any mail!");
					}
					

					}
				}
			}
		}

	//To give mail
	@EventHandler
	public void getMail(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
		Block block = e.getClickedBlock();
		Block blockbelow = block.getRelative(BlockFace.DOWN);
			if(block.getType() == Material.LOG && blockbelow.getType() == Material.FENCE) {
				
				if(block.getLocation().getBlockY() != plugin.getConfig().getInt("MailBoxs." + player.getWorld().getName() + "." + player.getName() + ".y") && block.getLocation().getBlockX() != plugin.getConfig().getInt("MailBoxs." + player.getWorld().getName() + "." + player.getName() + ".x") && block.getLocation().getBlockZ() != plugin.getConfig().getInt("MailBoxs." + player.getWorld().getName() + "." + player.getName() + ".z")) {
					
					if(player.getItemInHand().getType() == Material.WRITTEN_BOOK) {
						player.sendMessage("[MailBox]: " + ChatColor.GREEN + "Thanks for using Mailbox!");
						
						
						
						ItemStack is = player.getItemInHand();
						BookMeta bm = (BookMeta) is.getItemMeta();
						String author = bm.getAuthor();
						String title = bm.getTitle();
						
						List<String> pages = bm.getPages();
						
						ConfigurationSection sec = plugin.getConfig().getConfigurationSection(
								"MailBoxs." + block.getWorld().getName());

						Set<String> players = sec.getKeys(false);

						for (String pl : players) {
							Player play = Bukkit.getServer().getPlayer(pl);

							if (sec.getInt(play.getName() + ".y") == block.getY()
									&& sec.getInt(play.getName() + ".x") == block.getX()
									&& sec.getInt(play.getName() + ".z") == block.getZ()) {

								File txt = new File(plugin.getDataFolder(), title + ".txt");
								try {
									txt.createNewFile();
									
									BufferedWriter out = null;
									
									out = new BufferedWriter(new FileWriter(txt));
									
									out.write("The title of this book is: " + title);
									
									out.newLine();
									
									out.write("It was written by: " + author);
									
									out.newLine();
									
									for (int i = 0; i < pages.size(); i++) {
										
										String page = pages.get(i);
										
										String fixed = page.replace("\n", "   ");
										
										out.write("Page " + i + ": " + fixed);
										
										out.newLine();
									}
									
									out.close();
								} catch (IOException e1) {

									e1.printStackTrace();
								}
								ArrayList<String> book = new ArrayList<String>();
								
								if(plugin.getConfig().contains("MailBoxs." + play.getWorld().getName() + "." + play.getName() + ".books")){
									
									List<?> books = plugin.getConfig().getList("MailBoxs." + play.getWorld().getName() + "." + play.getName() + ".books");


									for(Object names : books){

										book.add(names.toString());

									}
								}
								
								book.add(title);
								plugin.getConfig().set("MailBoxs." + play.getPlayer().getWorld().getName() + "." + play.getPlayer().getName() + ".books", book);
								plugin.saveConfig();

							}

						} 

						
						
												
						
						
					}else {
						player.sendMessage("[MailBox]: " + ChatColor.RED + "You must write a letter first...");
					}
				}else {
					
				}
			}
		}
	}
	

}
