//package me.mrdaniel.crucialcraft.teleport;
//
//import java.util.function.Consumer;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.text.Text;
//
//import me.mrdaniel.crucialcraft.CCObject;
//import me.mrdaniel.crucialcraft.CrucialCraft;
//import me.mrdaniel.crucialcraft.utils.Messages;
//
//public class TPRequest extends CCObject implements Consumer<Player> {
//
//	private final Teleport tp;
//	private final long expires;
//	private final Text onclick;
//	private final Text ontp;
//
//	public TPRequest(@Nonnull final CrucialCraft cc, @Nonnull final Teleport tp, @Nonnull final Text onclick, @Nonnull final Text ontp) {
//		super(cc);
//
//		this.tp = tp;
//		this.expires = System.currentTimeMillis() + (super.getCrucialCraft().getConfig().getTeleportExpiry() * 1000);
//		this.onclick = onclick;
//		this.ontp = ontp;
//	}
//
//	@Override
//	public void accept(@Nonnull final Player p) {
//		if (System.currentTimeMillis() > this.expires) { Messages.TELEPORT_EXPIRED.send(p); return; }
//		p.sendMessage(this.onclick);
//		super.getCrucialCraft().getTeleportManager().add(p, this.tp, this.ontp);
//	}
//}