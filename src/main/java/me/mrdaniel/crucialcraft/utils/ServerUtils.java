package me.mrdaniel.crucialcraft.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.Server;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.spawn.EntitySpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.google.common.collect.Lists;

public class ServerUtils {

	@Nonnull
	public static Cause getGenericCause(@Nonnull final PluginContainer container, @Nonnull final NamedCause... causes) {
		return getCause(container, causes);
	}

	@Nonnull
	public static Cause getSpawnCause(@Nonnull final Entity e, @Nonnull final NamedCause... causes) {
		return getCause(EntitySpawnCause.builder().entity(e).type(SpawnTypes.PLUGIN).build(), causes);
	}

	@Nonnull
	public static Cause getCause(@Nonnull final Object root, @Nonnull final NamedCause... causes) {
		return Cause.source(root).addAll(Lists.newArrayList(causes)).build();
	}

	@Nonnull
	public static Entity spawn(@Nonnull final EntityType type, @Nonnull final Location<World> loc) {
		Entity e = loc.getExtent().createEntity(type, loc.getPosition());
		loc.getExtent().spawnEntity(e, getSpawnCause(e));
		return e;
	}

	public static double between(final double value, final double min, final double max) {
		return value < min ? min : value > max ? max : value;
	}

	public static void broadcast(@Nonnull final Server server, @Nonnull final Text message) {
		server.getOnlinePlayers().forEach(p -> p.sendMessage(message));
	}
}