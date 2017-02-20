package me.mrdaniel.crucialcraft.listeners;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.text.Text;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class ChatListener extends CCObject {

	public ChatListener(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Listener(order = Order.LATE)
	public void onChat(final MessageChannelEvent.Chat e, @Root final Player p) {
		if (super.getCrucialCraft().getPlayerData().get(p.getUniqueId()).isMuted()) { Messages.MUTED.send(p); e.setCancelled(true); return; }

		if (e.getRawMessage().toPlain().contains("&") && !p.hasPermission("cc.colors.chat")) { Messages.NO_COLOR_PERMISSION.send(p); e.setCancelled(true); return; }

		Subject subject = p.getContainingCollection().get(p.getIdentifier());

		Text name = super.getCrucialCraft().getPlayerData().get(p.getUniqueId()).getNick().orElse(Text.of(p.getName()));
		String msg = this.getChatMessage(name, subject, e.getRawMessage().toPlain());

		e.setMessage(TextUtils.toText(msg));
	}

	@Nonnull
	public String getChatMessage(@Nonnull final Text player, @Nonnull final Subject subject, @Nonnull final String message) {
		String style = super.getCrucialCraft().getConfig().getChatStyle();
		String format;

		if (style.equals("variable-based")) {
			String variable = super.getCrucialCraft().getConfig().getChatVariable();
			Optional<String> value = subject.getOption(variable);

			if (value.isPresent()) { format = super.getCrucialCraft().getConfig().getVariableChatFormat(value.get()).orElse(super.getCrucialCraft().getConfig().getGeneralChatFormat()); }
			else { format = super.getCrucialCraft().getConfig().getGeneralChatFormat(); }
		}
		else { format = super.getCrucialCraft().getConfig().getGeneralChatFormat(); }

		format = format.replace("%player", TextUtils.toString(player)).replace("%message", message);

		for (String variable : super.getCrucialCraft().getConfig().getVariables()) {
			Optional<String> value = subject.getOption(variable);
			format = format.replace("%" + variable, value.isPresent() ? super.getCrucialCraft().getConfig().getVariableText(variable, value.get()) : "");
		}
		return format;
	}
}