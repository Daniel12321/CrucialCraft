package me.mrdaniel.crucialcraft.teleport;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.spongepowered.api.Server;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import com.flowpowered.math.vector.Vector3d;

import me.mrdaniel.crucialcraft.CrucialCraft;

public class Teleport {

	protected final String world;
	protected final double x;
	protected final double y;
	protected final double z;
	protected final double pitch;
	protected final double yaw;

	public Teleport(@Nonnull final Transform<World> transform) { this(transform.getLocation(), transform.getRotation()); }
	public Teleport(@Nonnull final Location<World> loc, @Nonnull final Vector3d rotation) { this(loc.getExtent().getName(), loc.getX(), loc.getY(), loc.getZ(), rotation.getX(), rotation.getY()); }
	protected Teleport(@Nonnull final String world, final double x, final double y, final double z, final double pitch, final double yaw) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}

	@Nonnull
	public Optional<Transform<World>> getTransform(@Nonnull final Server server) {
		Optional<World> w = server.getWorld(this.world);
		if (!w.isPresent()) { return Optional.empty(); }

		return Optional.of(new Transform<World>(w.get(), new Vector3d(this.x, this.y, this.z), new Vector3d(this.pitch, this.yaw, 0.0), Vector3d.ONE));
	}

	public boolean teleport(@Nonnull final CrucialCraft cc, @Nonnull final Player p) {
		Optional<World> w = cc.getGame().getServer().getWorld(this.world);
		if (!w.isPresent()) { return false; }

		Teleport t = new Teleport(p.getLocation(), p.getHeadRotation());

		if (p.setLocationAndRotation(w.get().getLocation(this.x, this.y, this.z), new Vector3d(this.pitch, this.yaw, 0))) {
			cc.getPlayerData().get(p.getUniqueId()).setLastLocation(t);
			return true;
		}
		return false;
	}

	@Nonnull
	public String serialize() {
		return this.world + ":" + this.x + ":" + this.y + ":" + this.z + ":" + this.pitch + ":" + this.yaw;
	}

	@Nonnull
	public static Optional<Teleport> deserialize(@Nullable final String str) {
		try {
			String[] s = str.split(":");
			return Optional.of(new Teleport(s[0], Double.valueOf(s[1]), Double.valueOf(s[2]), Double.valueOf(s[3]), Double.valueOf(s[4]), Double.valueOf(s[5])));
		}
		catch (final Exception exc) { return Optional.empty(); }
	}
}