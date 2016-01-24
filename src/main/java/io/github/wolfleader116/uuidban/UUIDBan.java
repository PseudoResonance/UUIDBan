package io.github.wolfleader116.uuidban;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.JSONObject;

public class UUIDBan extends JavaPlugin implements Listener {

	protected static UUIDBan plugin;
	private static Config config;
	
	@Override
	public void onEnable() {
		plugin = this;
		Bukkit.getPluginManager().registerEvents(this, this);
		this.getCommand("uuidban").setExecutor(new UUIDBanC());
		this.getCommand("uuidunban").setExecutor(new UUIDBanC());
		config = new Config("uuids.yml", this);
	}
	
	@Override
	public void onDisable() {
		plugin = null;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerLoginEvent e) {
		if (e.getPlayer().isBanned()) {
			e.disallow(Result.KICK_BANNED, "Your account, " + e.getPlayer().getName() + ", is banned from this server!");
		} else {
			String UUID = "";
			JSONObject json = JsonReader.readJsonFromUrl("https://us.mc-api.net/v3/uuid/" + e.getPlayer().getName());
			try {
				UUID = json.getString("uuid");
			} catch (Exception ex) {
				UUID = "";
				ex.printStackTrace();
			}
			if (config.getConfig().contains(UUID)) {
				if (config.getConfig().getString(UUID).equalsIgnoreCase(e.getPlayer().getName())) {
					e.disallow(Result.KICK_BANNED, "Your account, " + config.getConfig().getString(UUID) + ", is banned from this server!");
				} else {
					config.getConfig().set(UUID, e.getPlayer().getName());
					config.save();
					e.disallow(Result.KICK_BANNED, "Your account, " + config.getConfig().getString(UUID) + ", is banned from this server!");
				}
			}
		}
	}

}
