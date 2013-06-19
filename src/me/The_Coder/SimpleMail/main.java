package me.The_Coder.SimpleMail;

import java.util.logging.Logger;

import me.The_Coder.SimpleMail.Listeners.BoxListener;
import me.The_Coder.SimpleMail.Listeners.GotMailListener;
import me.The_Coder.SimpleMail.Listeners.MailListener;


import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin{
	public Logger log = Logger.getLogger("Minecraft");
	public final MailListener maillistener = new MailListener(this);
	public final GotMailListener gotMail = new GotMailListener(this);
	public final BoxListener boxListener = new BoxListener(this);
	
	
	
	@Override
	public void onDisable() {
		log.info("[SimpleMailbox]: shuting down and saving config...");
		saveConfig();
		super.onDisable();
	}

	@Override
	public void onEnable() {
		
		getServer().getPluginManager().registerEvents(maillistener, this);
		getServer().getPluginManager().registerEvents(gotMail, this);
		getServer().getPluginManager().registerEvents(boxListener, this);
		
		log.info("[SimpleMailbox]: starting up and loading config...");
		
		if(!getConfig().contains("MailBoxs")) {

			getConfig().options().copyDefaults(true);
			saveConfig();
			
		}
	}
}
