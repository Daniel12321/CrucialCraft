package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.RayUtils;

public class CommandJump extends PlayerCommand {

	public CommandJump(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		Location<World> loc = RayUtils.getFirstBlock(target).orElseThrow(() -> new CommandException("No valid block was found."));

		Teleport t = new Teleport(target.getLocation(), target.getHeadRotation());

		if (target.setLocationSafely(loc.add(0.5, 1.0, 0.5))) { super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).setLastLocation(t); }
		else { throw new CommandException("No valid block found."); }
	}

	@Override
	public String getPermission() {
		return "cc.jump";
	}

	@Override
	public String getName() {
		return "Jump";
	}
}