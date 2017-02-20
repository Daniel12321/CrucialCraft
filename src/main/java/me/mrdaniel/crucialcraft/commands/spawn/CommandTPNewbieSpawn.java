package me.mrdaniel.crucialcraft.commands.spawn;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandTPNewbieSpawn extends PlayerCommand {

	public CommandTPNewbieSpawn(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		Teleport tp = super.getCrucialCraft().getDataFile().getNewbieSpawn().orElseThrow(() -> new CommandException("No newbie spawn was set."));
		tp.teleport(super.getCrucialCraft(), src, Text.of(TextColors.GOLD, "You were teleported to the newbie spawn."), false);
	}

	@Override
	public String getPermission() {
		return "cc.newbiespawn.tp";
	}

	@Override
	public String getName() {
		return "NewbieSpawn TP";
	}
}