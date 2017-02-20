package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandBack extends PlayerCommand {

	public CommandBack(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		Teleport tp = super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getLastLocation().orElseThrow(()-> new CommandException("Last location was not found"));

		if (!tp.teleport(super.getCrucialCraft(), src, Text.of(TextColors.GOLD, "You were teleported back to your last location."), false)) {
			throw new CommandException("This location doesnt exist anymore.");
		}
	}

	@Override
	public String getPermission() {
		return "cc.back";
	}

	@Override
	public String getName() {
		return "Back";
	}
}