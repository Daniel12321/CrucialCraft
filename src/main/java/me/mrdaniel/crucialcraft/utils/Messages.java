package me.mrdaniel.crucialcraft.utils;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;

public enum Messages {

	NOT_PLAYER(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "This command can only be used by players.")),
	NO_PERMISSION(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You dont have permission to use this command.")),
	NO_COLOR_PERMISSION(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You dont have permission to use colors.")),
	NO_KIT_PERMISSION(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You dont have permission to use this kit.")),
	NO_ITEM_IN_HAND(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You are not holding any item.")),
	ITEM_DOESNT_SUPPORT_DURABILITY(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "The item you are holding doesnt support item durability.")),
	SPAWN_NOT_SET(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "Spawn point is not set yet.")),
	TELEPORT_DOESNT_EXIST(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "This location doesnt exist anymore.")),
	NO_SUCH_HOME(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No home with that name exists.")),
	NO_SUCH_WORLD(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No world with that name exists.")),
	NO_SUCH_WARP(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No warp with this name exists.")),
	NO_SUCH_JAIL(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No jail with this name exists.")),
	NO_SUCH_GAMEMODE(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No gamemode with this name exists.")),
	NO_SUCH_TIME(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No time with this name exists.")),
	NO_SUCH_KIT(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No kit with this name exists.")),
	NO_SUCH_WEATHER(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No weather type with this name exists.")),
	NO_SUCH_NICK(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No player with this nickname exists.")),
	NO_SUCH_USER(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "Could not find any user with this name.")),
	IS_NOT_JAILED(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "This person is not jailed.")),
	NO_TARGET_YOURSELF(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You cant target yourself.")),
	NO_LAST_LOCATION(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No last location was found.")),
	NO_LAST_MESSAGER(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No last messager was found.")),
	NO_MORE_HOMES(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No more homes available.")),
	MUST_BE_POSITIVE(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "Value must be above 0.")),
	NO_BLOCK_FOUND(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "Failed to find a valid block.")),
	MUST_SPECIFY_WORLD(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "You must specify a world.")),
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