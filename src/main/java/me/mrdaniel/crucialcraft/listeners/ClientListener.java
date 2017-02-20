package me.mrdaniel.crucialcraft.listeners;

import java.time.Instant;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.ban.Ban;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.teleport.Teleport;
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
		boolean firstjoin = super.getCrucialCraft().getPlayerData().load(e.getTargetEntity().getUniqueId());
		if (firstjoin) { this.onFirstJoin(e.getTargetEntity()); }

		e.setMessage(super.getCrucialCraft().getConfig().getLoginMessage(e.getTargetEntity().getName()));
		e.getTargetEntity().sendMessages(super.getCrucialCraft().getConfig().getMotd(e.getTargetEntity().getName(), super.getCrucialCraft().getGame().getServer().getOnlinePlayers().size(), super.getCrucialCraft().getGame().getServer().getMaxPlayers()));

		super.getCrucialCraft().getPlayerData().get(e.getTargetEntity().getUniqueId()).setLastLogin(System.currentTimeMillis());
	}

	private void onFirstJoin(@Nonnull final Player p) {
		ServerUtils.broadcast(super.getCrucialCraft().getGame().getServer(), super.getCrucialCraft().getConfig().getFirstJoinMessage(p.getName()));

		Optional<Teleport> newbiespawn = super.getCrucialCraft().getDataFile().getNewbieSpawn();
		if (!(newbiespawn.isPresent() && newbiespawn.get().teleport(super.getCrucialCraft(), p, null, true))) {
			super.getCrucialCraft().getDataFile().getSpawn().ifPresent(spawn -> spawn.teleport(super.getCrucialCraft(), p, null, true));
		}
		if (super.getCrucialCraft().getConfig().isKitsEnabled() && super.getCrucialCraft().getConfig().isFirstJoinKit()) {
			super.getCrucialCraft().getGame().getCommandManager().process(p, "kit " + super.getCrucialCraft().getConfig().getFirstJoinKit());
		}
	}

	@Listener(order = Order.LATE)
	public void onQuit(final ClientConnectionEvent.Disconnect e) {
		e.setMessage(super.getCrucialCraft().getConfig().getLogoutMessage(e.getTargetEntity().getName()));

		PlayerFile file = super.getCrucialCraft().getPlayerData().get(e.getTargetEntity().getUniqueId());
		file.setPlaytime(file.getCurrentPlaytime());
		file.setLastLogout(System.currentTimeMillis());
	}
}