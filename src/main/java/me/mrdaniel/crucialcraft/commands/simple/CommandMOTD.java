package me.mrdaniel.crucialcraft.commands.simple;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;

public class CommandMOTD extends PermissionCommand {

	public CommandMOTD(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		src.sendMessages(super.getCrucialCraft().getConfig().getMotd(src instanceof Player ? ((Player)src).getName() : "Console", super.getCrucialCraft().getGame().getServer().getOnlinePlayers().size(), super.getCrucialCraft().getGame().getServer().getMaxPlayers()));
	}

	@Override
	public String getPermission() {
		return "cc.motd";
	}
}