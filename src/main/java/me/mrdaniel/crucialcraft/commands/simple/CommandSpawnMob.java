package me.mrdaniel.crucialcraft.commands.simple;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandSpawnMob extends TargetPlayerCommand {

	public CommandSpawnMob(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		EntityType type = args.<EntityType>getOne("type").get();
		int amount = args.<Integer>getOne("amount").orElse(1);
		if (amount < 1) { Messages.MUST_BE_POSITIVE.send(src.orElse(target)); return; }

		List<Entity> l = Lists.newArrayList();
		for (int i = 0; i < amount; i++) { l.add(target.getWorld().createEntity(type, target.getLocation().getPosition())); }

		target.getWorld().spawnEntities(l, Cause.source(EntitySpawnCause.builder().entity(l.get(0)).type(SpawnTypes.PLUGIN).build()).named(NamedCause.OWNER, super.getCrucialCraft().getContainer()).build());
	}

	@Override
	public String getPermission() {
		return "cc.spawnmob";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}