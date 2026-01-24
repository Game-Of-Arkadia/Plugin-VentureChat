package mineverse.Aust1n46.chat.command.chat;

import static mineverse.Aust1n46.chat.MineverseChat.LINE_LENGTH;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import mineverse.Aust1n46.chat.MineverseChat;
import mineverse.Aust1n46.chat.api.MineverseChatAPI;
import mineverse.Aust1n46.chat.api.MineverseChatPlayer;
import mineverse.Aust1n46.chat.channel.ChatChannel;
import mineverse.Aust1n46.chat.localization.LocalizedMessage;
import org.jetbrains.annotations.NotNull;

public class Chwho extends Command {
	private MineverseChat plugin = MineverseChat.getInstance();

	public Chwho() {
		super("chwho");
	}

	@Override
	public boolean execute(@NotNull CommandSender sender, @NotNull String command, @NotNull String @NotNull [] args) {
		String playerlist = "";
		if (sender.hasPermission("venturechat.chwho")) {
			if (args.length > 0) {
				ChatChannel channel = ChatChannel.getChannel(args[0]);
				if (channel != null) {
					if (channel.hasPermission()) {
						if (!sender.hasPermission(channel.getPermission())) {
							MineverseChatPlayer mcp = MineverseChatAPI.getOnlineMineverseChatPlayer(((Player) sender));
							mcp.removeListening(channel.getName());
							mcp.getPlayer().sendMessage(LocalizedMessage.CHANNEL_NO_PERMISSION_VIEW.toString());
							return true;
						}
					}

					if (channel.getBungee() && sender instanceof Player) {
						MineverseChatPlayer mcp = MineverseChatAPI.getOnlineMineverseChatPlayer((Player) sender);
						ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
						DataOutputStream out = new DataOutputStream(byteOutStream);
						try {
							out.writeUTF("Chwho");
							out.writeUTF("Get");
							out.writeUTF(mcp.getUUID().toString());
							out.writeUTF(channel.getName());
							mcp.getPlayer().sendPluginMessage(plugin, MineverseChat.PLUGIN_MESSAGING_CHANNEL, byteOutStream.toByteArray());
							out.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return true;
					}

					PluginManager pluginManager = plugin.getServer().getPluginManager();
					long linecount = LINE_LENGTH;
					for (MineverseChatPlayer p : MineverseChatAPI.getOnlineMineverseChatPlayers()) {
						if (p.getListening().contains(channel.getName())) {
							if (sender instanceof Player) {
								if (!((Player) sender).canSee(p.getPlayer())) {
									continue;
								}
							}
							if (channel.hasDistance() && sender instanceof Player) {
								if (!this.isPlayerWithinDistance((Player) sender, p.getPlayer(), channel.getDistance())) {
									continue;
								}
							}
							if (playerlist.length() + p.getName().length() > linecount) {
								playerlist += "\n";
								linecount = linecount + LINE_LENGTH;
							}
							if (!p.isMuted(channel.getName())) {
								playerlist += "§f" + p.getName();
							} else {
								playerlist += "§c" + p.getName();
							}
							playerlist += "§f, ";
						}
					}
					if (playerlist.length() > 2) {
						playerlist = playerlist.substring(0, playerlist.length() - 2);
					}
					sender.sendMessage(LocalizedMessage.CHANNEL_PLAYER_LIST_HEADER.toString().replace("{channel_color}", (channel.getColor())).replace("{channel_name}",
							channel.getName()));
					sender.sendMessage(playerlist);
					return true;
				} else {
					sender.sendMessage(LocalizedMessage.INVALID_CHANNEL.toString().replace("{args}", args[0]));
					return true;
				}
			} else {
				sender.sendMessage(LocalizedMessage.COMMAND_INVALID_ARGUMENTS.toString().replace("{command}", "/chwho").replace("{args}", "[channel]"));
				return true;
			}
		} else {
			sender.sendMessage(LocalizedMessage.COMMAND_NO_PERMISSION.toString());
			return true;
		}
	}

	private boolean isPlayerWithinDistance(Player p1, Player p2, double chDistance) {
		Location locSender = p1.getLocation();
		Location diff;
		if (chDistance > (double) 0) {
			Location locReceipter = p2.getLocation();
			if (locReceipter.getWorld() == p1.getWorld()) {
				diff = locReceipter.subtract(locSender);
        return !(Math.abs(diff.getX()) > chDistance) && !(Math.abs(diff.getZ()) > chDistance) && !(Math.abs(diff.getY()) > chDistance);
			} else {
				return false;
			}
		}
		return true;
	}
}
