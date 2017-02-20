package me.mrdaniel.crucialcraft.command;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public abstract class TargetUserCommand extends SimpleCommand {

	public TargetUserCommand(@Nonnull final CrucialCraft cc, @Nonnull final Argument... args) {
		super(cc, args);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		if (!args.has("target")) { src.sendMessage(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No target user found.")); return; }
		this.execute(src, args.<User>get("target"), args);
	}

	@Override public String getPermission(@Nonnull final Arguments args) { return this.getPermission(); }
	public abstract String getPermission();
	public abstract void execute(final CommandSource src, final User target, final Arguments args) throws CommandException;
}