package me.mrdaniel.crucialcraft.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;

public class PlayerListener extends CCObject {

	public PlayerListener(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Listener(order = Order.LATE)
	public void onPlayerDeath(final DestructEntityEvent.Death e) {
		if (e.getTargetEntity() instanceof Player) {
			Player p = (Player) e.getTargetEntity();

			CCPlayerData data = p.get(CCPlayerData.class).get();
			data.setLastLocation(new Teleport(p.getLocation(), p.getHeadRotation()));
			p.offer(data);
		}
	}

	@Listener(order = Order.LATE)
	public void onRespawn(final RespawnPlayerEvent e) {
		super.getCrucialCraft().getDataFile().getSpawn().ifPresent(spawn -> spawn.getTransform(super.getCrucialCraft().getGame().getServer()).ifPresent(trans -> e.setToTransform(trans)));
	}
}