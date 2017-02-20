package me.mrdaniel.crucialcraft.commands.spawn;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandSpawn extends TargetSelfOrOtherPlayerCommand {

	public CommandSpawn(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) throws CommandException {
		Teleport spawn = super.getCrucialCraft().getDataFile().getSpawn().orElseThrow(() -> new CommandException("No spawn location was set."));

		if (spawn.teleport(super.getCrucialCraft(), target, Text.of(TextColors.GOLD, "You were teleported to spawn."), src.isPresent())) {
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to spawn.")));
		}
		else throw new CommandException("This location doesnt exist anymore.");
	}

	@Override
	public String getPermission() {
		return "cc.spawn";
	}


	@Override
	public String getName() {
		return "Spawn";
	}
}