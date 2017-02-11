package me.mrdaniel.crucialcraft.listeners;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.service.permission.Subject;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class ChatListener extends CCObject {

	public ChatListener(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Listener(order = Order.LATE)
	public void onChat(final MessageChannelEvent.Chat e, @Root final Player p) {
		if (e.getRawMessage().toPlain().contains("&") && !p.hasPermission("cc.colors.chat")) { Messages.NO_COLOR_PERMISSION.send(p); return; }

		Subject subject = p.getContainingCollection().get(p.getIdentifier());

		String msg = super.getCrucialCraft().getConfig().getChatMessage(p.get(CCPlayerData.class).get().getNick().orElse(p.getName()), subject, e.getRawMessage().toPlain());

		e.setMessage(TextUtils.toText(msg));
	}
}