//package me.mrdaniel.crucialcraft.teleport;
//
//import java.util.HashMap;
//import java.util.UUID;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.entity.Entity;
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.event.Listener;
//import org.spongepowered.api.event.entity.MoveEntityEvent;
//import org.spongepowered.api.event.filter.Getter;
//import org.spongepowered.api.scheduler.Task;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.format.TextColors;
//
//import com.google.common.collect.Maps;
//
//import me.mrdaniel.crucialcraft.CCObject;
//import me.mrdaniel.crucialcraft.CrucialCraft;
//import me.mrdaniel.crucialcraft.utils.Messages;
//
//public class TeleportManager extends CCObject {
//
//	private final HashMap<UUID, Task> teleports;
//
//	public TeleportManager(@Nonnull final CrucialCraft cc) {
//		super(cc);
//
//		this.teleports = Maps.newHashMap();
//	}
//
//	public void add(@Nonnull final Player p, @Nonnull final Teleport tp, @Nonnull final Text ontp) {
//		this.teleports.put(p.getUniqueId(), Task.builder().delayTicks(super.getCrucialCraft().getConfig().getTeleportDelay() * 20).execute(() -> {
//			if (tp.teleport(super.getCrucialCraft(), p)) { p.sendMessage(ontp); }
//			else { Messages.TELEPORT_DOESNT_EXIST.send(p); }
//		}).submit(super.getCrucialCraft()));
//	}
//
//	@Listener
//	public void onMove(final MoveEntityEvent e, @Getter("getTargetEntity") final Entity ent) {
//		if (this.teleports.containsKey(ent.getUniqueId())) {
//			this.teleports.get(ent.getUniqueId()).cancel();
//			((Player)ent).sendMessage(Text.of(TextColors.GOLD, "You moved! Teleportation cancelled."));
//		}
//	}
//}