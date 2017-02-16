package me.mrdaniel.crucialcraft.utils;

import java.time.Instant;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import me.mrdaniel.crucialcraft.teleport.TPRequest;

public class TextUtils {

	@Nonnull
	public static Text toText(@Nonnull final String text) {
		return TextSerializers.formattingCode('&').deserialize(text);
	}

	@Nonnull
	public static String toString(@Nonnull final Text text) {
		return TextSerializers.formattingCode('&').serialize(text);
	}

	@Nonnull
	public static Text getAcceptText(@Nonnull final TPRequest req, @Nonnull final Player p) {
		return Text.builder().append(Text.of(TextColors.RED, "accept")).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Click here to ", TextColors.RED, "accept", TextColors.GOLD, "."))).onClick(TextActions.executeCallback(src -> req.accept(src))).build();
	}

	@Nonnull
	public static Text getDenyText(@Nonnull final TPRequest req) {
		return Text.builder().append(Text.of(TextColors.RED, "deny")).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Click here to ", TextColors.RED, "deny", TextColors.GOLD, "."))).onClick(TextActions.executeCallback(src -> req.deny(src))).build();
	}

	@Nonnull
	public static String getTimeRemainingFormat(@Nonnull final Instant expiration) {
		return getTimeFormat(expiration.toEpochMilli() - System.currentTimeMillis());
	}

	@Nonnull
	public static String getTimeFormat(final long millis) {
		int seconds = (int) ((millis) / 1000);
		int minutes = 0;
		int hours = 0;
		int days = 0;
		while (seconds >= 86400) { days++; seconds -= 86400; }
		while (seconds >= 3600) { hours++; seconds -= 3600; }
		while (seconds >= 60) { minutes++; seconds -= 60; }

		String str = "";
		if (days > 0) { str += days + "d"; }
		if (hours > 0) { str += (str.equals("") ? "" : " ") + hours + "h"; }
		if (minutes > 0) { str += (str.equals("") ? "" : " ") + minutes + "m"; }
		if (seconds > 0) { str += (str.equals("") ? "" : " ") + seconds + "s"; }
		return str;
	}
}