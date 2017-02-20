package me.mrdaniel.crucialcraft.commands;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandSpawnMob extends PlayerCommand {

	public CommandSpawnMob(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.entitytype(cc, "type"), Argument.optional(Argument.integer("amount")));
	}

	@Override
	public void execute(final Player src, final Arguments args) {
		EntityType type = args.get("type");
		int amount = (int) ServerUtils.between(args.has("amount") ? args.<Integer>get("amount") : 1, 1, 10);
		

		List<Entity> l = Lists.newArrayList();
		for (int i = 0; i < amount; i++) { l.add(src.getWorld().createEntity(type, src.getLocation().getPosition())); }

		src.getWorld().spawnEntities(l, Cause.source(EntitySpawnCause.builder().entity(l.get(0)).type(SpawnTypes.PLUGIN).build()).named(NamedCause.OWNER, super.getCrucialCraft().getContainer()).build());
	}

	@Override
	public String getPermission() {
		return "cc.spawnmob";
	}

	@Override
	public String getName() {
		return "Spawnmob";
	}
}