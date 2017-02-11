package me.mrdaniel.crucialcraft.listeners;

import java.time.Instant;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.ban.Ban;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.utils.ServerUtils;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class ClientListener extends CCObject {

	public ClientListener(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Listener(order = Order.LATE)
	public void onAuth(final ClientConnectionEvent.Auth e) {
		Optional<Ban.Profile> gpb = super.getCrucialCraft().getBans().getBanFor(e.getProfile());
		Optional<Ban.Ip> ipb = super.getCrucialCraft().getBans().getBanFor(e.getConnection().getAddress().getAddress());

		if (gpb.isPresent() || ipb.isPresent()) {
			Ban ban = gpb.isPresent() ? gpb.get() : ipb.get();
			if (ban.getExpirationDate().isPresent()) {
				if (ban.getExpirationDate().get().isBefore(Instant.now())) {
					super.getCrucialCraft().getBans().pardon(e.getProfile());
				}
				else {
					e.setCancelled(true);
					e.setMessage(super.getCrucialCraft().getConfig().getTempBanMessage(ban.getReason().orElse(Text.of("Not Specified.")), ban.getBanSource().orElse(Text.of("the server")), TextUtils.getTimeRemainingFormat(ban.getExpirationDate().get())));
				}
			}
			else {
				e.setCancelled(true);
				e.setMessage(super.getCrucialCraft().getConfig().getBanMessage(ban.getReason().orElse(Text.of("Not Specified.")), ban.getBanSource().orElse(Text.of("the server"))));
			}
		}
		else if (super.getCrucialCraft().getGame().getServer().hasWhitelist()) {
			if (!super.getCrucialCraft().getWhitelist().isWhitelisted(e.getProfile())) {
				e.setCancelled(true);
				e.setMessage(super.getCrucialCraft().getConfig().getWhitelistMessage());
			}
		}
	}

	@Listener(order = Order.LATE)
	public void onLogin(final ClientConnectionEvent.Login e) {
		
	}

	@Listener(order = Order.LATE)
	public void onJoin(final ClientConnectionEvent.Join e) {
		e.setMessage(super.getCrucialCraft().getConfig().getLoginMessage(e.getTargetEntity().getName()));
		e.getTargetEntity().sendMessages(super.getCrucialCraft().getConfig().getMotd(e.getTargetEntity().getName(), super.getCrucialCraft().getGame().getServer().getOnlinePlayers().size(), super.getCrucialCraft().getGame().getServer().getMaxPlayers()));

		if (!e.getTargetEntity().get(CCPlayerData.class).isPresent()) {
			ServerUtils.broadcast(super.getCrucialCraft().getGame().getServer(), super.getCrucialCraft().getConfig().getFirstJoinMessage(e.getTargetEntity().getName()));
			e.getTargetEntity().offer(new CCPlayerData(super.getCrucialCraft()));
			Optional<Teleport> newbiespawn = super.getCrucialCraft().getDataFile().getNewbieSpawn();
			if (!(newbiespawn.isPresent() && newbiespawn.get().teleport(super.getCrucialCraft(), e.getTargetEntity()))) {
				super.getCrucialCraft().getDataFile().getSpawn().ifPresent(spawn -> spawn.teleport(super.getCrucialCraft(), e.getTargetEntity()));
			}

			// New Player
			
		}
	}

	@Listener(order = Order.LATE)
	public void onQuit(final ClientConnectionEvent.Disconnect e) {
		e.setMessage(super.getCrucialCraft().getConfig().getLogoutMessage(e.getTargetEntity().getName()));
	}
}