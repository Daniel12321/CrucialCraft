package me.mrdaniel.crucialcraft.utils;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;

public enum Messages {

	NO_PERMISSION(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You dont have permission to use this command.")),
	NO_COLOR_PERMISSION(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You dont have permission to use colors.")),
	TELEPORT_DOESNT_EXIST(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "This location doesnt exist anymore.")),
	TELEPORT_EXPIRED(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "This teleport request expired.")),
	MUTED(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You cant talk while muted."))
	;

	private final Text message;

	Messages(Text message) {
		this.message = message;
	}

	@Nonnull
	public <T extends MessageReceiver> Messages send(@Nonnull final T receiver) {
		receiver.sendMessage(this.message); return this;
	}

	@Nonnull
	public <T extends MessageReceiver> Messages send(@Nonnull final Optional<T> receiver) {
		receiver.ifPresent(rec -> this.send(rec)); return this;
	}
}