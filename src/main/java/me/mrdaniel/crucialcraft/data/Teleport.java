package me.mrdaniel.crucialcraft.data;

import java.util.Optional;

import javax.annotation.Nonnull;

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

		CCPlayerData data = p.get(CCPlayerData.class).get();
		data.setLastLocation(new Teleport(p.getLocation(), p.getHeadRotation()));
		p.offer(data);

		p.setLocationAndRotation(w.get().getLocation(this.x, this.y, this.z), new Vector3d(this.pitch, this.yaw, 0));
		return true;
	}
}