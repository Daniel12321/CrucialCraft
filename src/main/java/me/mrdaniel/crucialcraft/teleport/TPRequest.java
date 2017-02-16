package me.mrdaniel.crucialcraft.teleport;

import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.Messages;

public class TPRequest extends CCObject {

	private final UUID totp;
	private final Teleport tp;
	private final long expires;
	private final Text onclick;
	private final Text ontp;

	private boolean denied;
	private boolean done;

	public TPRequest(@Nonnull final CrucialCraft cc, @Nonnull final UUID totp, @Nonnull final Teleport tp, @Nonnull final Text onclick, @Nonnull final Text ontp) {
		super(cc);

		this.totp = totp;
		this.tp = tp;
		this.expires = System.currentTimeMillis() + (super.getCrucialCraft().getConfig().getTeleportExpiry() * 1000);
		this.onclick = onclick;
		this.ontp = ontp;

		this.denied = false;
	}

	public void accept(@Nonnull final CommandSource src) {
		super.getCrucialCraft().getGame().getServer().getPlayer(this.totp).ifPresent(p -> {
			if (this.done) { src.sendMessage(Text.of(TextColors.GOLD, "This request was already accepted.")); return; }
			if (this.denied) { src.sendMessage(Text.of(TextColors.GOLD, "This request was already denied.")); return; }
			if (System.currentTimeMillis() > this.expires) { Messages.TELEPORT_EXPIRED.send(src); return; }
			p.sendMessage(this.onclick);
			super.getCrucialCraft().getTeleportManager().add(p, this.tp, this.ontp);
		 });
	}

	public void deny(@Nonnull final CommandSource src) {
		if (this.done) { src.sendMessage(Text.of(TextColors.GOLD, "This request was already accepted.")); }
		else if (this.denied) { src.sendMessage(Text.of(TextColors.GOLD, "This request was already denied.")); }
		else {
			this.denied = true;
			src.sendMessage(Text.of(TextColors.GOLD, "You denied the teleport request."));
		}
	}
}