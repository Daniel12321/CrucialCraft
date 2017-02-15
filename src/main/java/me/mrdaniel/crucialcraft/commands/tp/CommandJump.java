package me.mrdaniel.crucialcraft.commands.tp;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.RayUtils;

public class CommandJump extends PlayerCommand {

	public CommandJump(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		Optional<Location<World>> loc = RayUtils.getFirstBlock(target);
		if (!loc.isPresent()) { Messages.NO_BLOCK_FOUND.send(target); return; }

		Teleport t = new Teleport(target.getLocation(), target.getHeadRotation());

		if (target.setLocationSafely(loc.get().add(0.5, 1.0, 0.5))) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			file.setLastLocation(t);
		}
		else { Messages.NO_BLOCK_FOUND.send(target); }
	}

	@Override
	public String getPermission() {
		return "cc.jump";
	}
}