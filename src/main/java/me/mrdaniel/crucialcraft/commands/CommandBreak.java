package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.RayUtils;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandBreak extends PlayerCommand {

	public CommandBreak(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		Location<World> loc = RayUtils.getFirstBlock(target).orElseThrow(() -> new CommandException("No valid block was found."));

		loc.getExtent().digBlock(loc.getBlockPosition(), ServerUtils.getCause(super.getCrucialCraft().getContainer(), NamedCause.simulated(target)));
	}

	@Override
	public String getPermission() {
		return "cc.break";
	}

	@Override
	public String getName() {
		return "Break";
	}
}