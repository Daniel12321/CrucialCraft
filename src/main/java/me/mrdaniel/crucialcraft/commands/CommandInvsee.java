package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerTargetOtherPlayerCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandInvsee extends PlayerTargetOtherPlayerCommand {

	public CommandInvsee(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.player(cc, "target"));
	}

	@Override
	public void execute(final Player target, final Player src, final Arguments args) {
		src.openInventory(target.getInventory(), ServerUtils.getCause(super.getCrucialCraft().getContainer()));
	}

	@Override
	public String getPermission() {
		return "cc.invsee";
	}

	@Override
	public String getName() {
		return "Invsee";
	}
}