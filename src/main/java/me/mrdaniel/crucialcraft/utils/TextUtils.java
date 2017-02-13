package me.mrdaniel.crucialcraft.utils;

import java.time.Instant;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextUtils {

	@Nonnull
	public static Text toText(@Nonnull final String message) {
		return TextSerializers.formattingCode('&').deserialize(message);
	}

	@Nonnull
	public static String toString(@Nonnull final Text message) {
		return TextSerializers.formattingCode('&').serialize(message);
	}

	@Nonnull
	public static String getTimeRemainingFormat(@Nonnull final Instant expiration) {
		return getTimeFormat((int)((expiration.toEpochMilli() - System.currentTimeMillis()) / 1000));
	}

	@Nonnull
	public static String getTimeFormat(int seconds) {
		int minutes = 0;
		int hours = 0;
		int days = 0;
		while (seconds >= 86400) { days++; seconds -= 86400; }
		while (seconds >= 3600) { hours++; seconds -= 3600; }
		while (seconds >= 60) { minutes++; seconds -= 60; }

		String str = "";
		if (days > 0) { str += days + " Days"; }
		if (hours > 0) { str += (str.equals("") ? "" : " ") + hours + " Hours"; }
		if (minutes > 0) { str += (str.equals("") ? "" : " ") + minutes + " Minutes"; }
		if (seconds > 0) { str += (str.equals("") ? "" : " ") + seconds + " Seconds"; }
		return str;
	}
}