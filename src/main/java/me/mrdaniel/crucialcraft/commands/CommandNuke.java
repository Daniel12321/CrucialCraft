package me.mrdaniel.crucialcraft.commands;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandNuke extends TargetSelfOrOtherPlayerCommand {

	public CommandNuke(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public String getPermission() {
		return "cc.nuke";
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) throws CommandException {
		Location<World> l = target.getLocation();

		List<Entity> tnts = Lists.newArrayList();
		for (int i = 0; i < 10; i++) {
			Vector3d pos = new Vector3d(l.getX() + (Math.random() * 50) - 25, 256, l.getZ() + (Math.random() * 50) - 25);
			tnts.add(l.getExtent().createEntity(EntityTypes.PRIMED_TNT, pos));
		}
		tnts.forEach(tnt -> { tnt.offer(Keys.FUSE_DURATION, 120); tnt.offer(Keys.EXPLOSION_RADIUS, Optional.of(0)); });
		l.getExtent().spawnEntities(tnts, ServerUtils.getSpawnCause(tnts.get(0)));
	}

	@Override
	public String getName() {
		return "Nuke";
	}
}