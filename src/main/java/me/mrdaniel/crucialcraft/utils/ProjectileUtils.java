package me.mrdaniel.crucialcraft.utils;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Projectile;

import com.flowpowered.math.vector.Vector3d;

public class ProjectileUtils {

	public static <T extends Projectile> T launchProjectile(@Nonnull final Player p, @Nonnull final Class<T> projectileClass, final double speed) {
		T projectile = p.launchProjectile(projectileClass).get();
		launch(p, projectile, speed);
		return projectile;
	}

	public static void launch(@Nonnull final Living launcher, @Nonnull final Entity toLaunch, final double speed) {

		double pitch = launcher.getHeadRotation().getX();
		double yaw  = launcher.getHeadRotation().getY();

		float f = 0.4F;
        double x = (double) (-Math.sin(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI) * f);
        double y = (double) (-Math.sin((pitch) / 180.0F * (float) Math.PI) * f);
        double z = (double) (Math.cos(yaw / 180.0F * (float) Math.PI) * Math.cos(pitch / 180.0F * (float) Math.PI) * f);

		Vector3d velocity = new Vector3d(x,y,z).mul(speed);
		toLaunch.setVelocity(velocity);
	}
}