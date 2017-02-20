package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;

public class CommandMOTD extends SimpleCommand {

	public CommandMOTD(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) {
		src.sendMessages(super.getCrucialCraft().getConfig().getMotd(src instanceof Player ? ((Player)src).getName() : "Console", super.getCrucialCraft().getGame().getServer().getOnlinePlayers().size(), super.getCrucialCraft().getGame().getServer().getMaxPlayers()));
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.motd";
	}

	@Override
	public String getName() {
		return "MOTD";
	}
}