package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.RayUtils;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandBreak extends PlayerCommand {

	public CommandBreak(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		Optional<Location<World>> loc = RayUtils.getFirstBlock(target);

		if (loc.isPresent()) { loc.get().getExtent().digBlock(loc.get().getBlockPosition(), ServerUtils.getCause(super.getCrucialCraft().getContainer(), NamedCause.simulated(target))); }
		else { Messages.NO_BLOCK_FOUND.send(target); }
	}

	@Override
	public String getPermission() {
		return "cc.break";
	}
}