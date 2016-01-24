package io.github.wolfleader116.uuidban;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.json.JSONObject;

public class UUIDBanC implements CommandExecutor {

	private static final Logger log = Logger.getLogger("Minecraft");
	
	private Config config = new Config("uuids.yml", UUIDBan.plugin);

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("uuidban")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("uuidban.ban")) {
					String UUID = "";
					JSONObject json = JsonReader.readJsonFromUrl("https://us.mc-api.net/v3/uuid/" + args[0]);
					try {
						UUID = json.getString("uuid");
					} catch (Exception ex) {
						UUID = "";
						ex.printStackTrace();
					}
					if (config.getConfig().contains(UUID)) {
						if (config.getConfig().getString(UUID).equalsIgnoreCase(args[1])) {
							sender.sendMessage(ChatColor.BLUE + "UUIDBan> " + ChatColor.GREEN + args[0] + " is already uuid banned!");
							if (Bukkit.getServer().getPlayer(args[0]) != null) {
								Bukkit.getServer().getPlayer(args[0]).kickPlayer("Your account, " + Bukkit.getServer().getPlayer(args[0]).getName() + ", is banned from this server!");
							}
						} else {
							config.getConfig().set(UUID, args[1]);
							config.save();
							sender.sendMessage(ChatColor.BLUE + "UUIDBan> " + ChatColor.GREEN + args[0] + " updated after uuid change. Player is still banned.");
							if (Bukkit.getServer().getPlayer(args[0]) != null) {
								Bukkit.getServer().getPlayer(args[0]).kickPlayer("Your account, " + Bukkit.getServer().getPlayer(args[0]).getName() + ", is banned from this server!");
							}
						}
					} else {
						config.getConfig().set(UUID, args[1]);
						config.save();
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Your account, " + Bukkit.getServer().getPlayer(args[0]).getName() + ", is banned from this server!");
						}
						sender.sendMessage(ChatColor.BLUE + "UUIDBan> " + ChatColor.GREEN + args[0] + " is now uuid banned!");
					}
				} else {
					sender.sendMessage(ChatColor.BLUE + "UUIDBan> " + ChatColor.GREEN + "You do not have permission to use this command!");
				}
			} else {
				String UUID = "";
				JSONObject json = JsonReader.readJsonFromUrl("https://us.mc-api.net/v3/uuid/" + args[0]);
				try {
					UUID = json.getString("uuid");
				} catch (Exception ex) {
					UUID = "";
					ex.printStackTrace();
				}
				if (config.getConfig().contains(UUID)) {
					if (config.getConfig().getString(UUID).equalsIgnoreCase(args[1])) {
						log.info(args[0] + " is already banned!");
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Your account, " + Bukkit.getServer().getPlayer(args[0]).getName() + ", is banned from this server!");
						}
					} else {
						config.getConfig().set(UUID, args[1]);
						config.save();
						log.info(args[0] + " updated after uuid change. Player is still banned.");
						if (Bukkit.getServer().getPlayer(args[0]) != null) {
							Bukkit.getServer().getPlayer(args[0]).kickPlayer("Your account, " + Bukkit.getServer().getPlayer(args[0]).getName() + ", is banned from this server!");
						}
					}
				} else {
					config.getConfig().set(UUID, args[1]);
					config.save();
					if (Bukkit.getServer().getPlayer(args[0]) != null) {
						Bukkit.getServer().getPlayer(args[0]).kickPlayer("Your account, " + Bukkit.getServer().getPlayer(args[0]).getName() + ", is banned from this server!");
					}
					log.info(args[0] + " is now uuid banned!");
				}
			}
		} else if (cmd.getName().equalsIgnoreCase("uuidunban")) {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				if (player.hasPermission("uuidban.unban")) {
					String UUID = "";
					JSONObject json = JsonReader.readJsonFromUrl("https://us.mc-api.net/v3/uuid/" + args[0]);
					try {
						UUID = json.getString("uuid");
					} catch (Exception ex) {
						UUID = "";
						ex.printStackTrace();
					}
					if (config.getConfig().contains(UUID)) {
						config.getConfig().set(UUID, null);
						config.save();
						sender.sendMessage(ChatColor.BLUE + "UUIDBan> " + ChatColor.GREEN + args[0] + " is now unbanned!");
					} else {
						sender.sendMessage(ChatColor.BLUE + "UUIDBan> " + ChatColor.GREEN + args[0] + " is not already uuid bannned!");
					}
				} else {
					sender.sendMessage(ChatColor.BLUE + "UUIDBan> " + ChatColor.GREEN + "You do not have permission to use this command!");
				}
			} else {
				String UUID = "";
				JSONObject json = JsonReader.readJsonFromUrl("https://us.mc-api.net/v3/uuid/" + args[0]);
				try {
					UUID = json.getString("uuid");
				} catch (Exception ex) {
					UUID = "";
					ex.printStackTrace();
				}
				if (config.getConfig().contains(UUID)) {
					config.getConfig().set(UUID, null);
					config.save();
					log.info(args[0] + " is now unbanned!");
				} else {
					log.info(args[0] + " is not already uuid banned!");
				}
			}
		}
		return false;
	}

}
