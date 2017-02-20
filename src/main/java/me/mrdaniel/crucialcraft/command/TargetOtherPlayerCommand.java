package me.mrdaniel.crucialcraft.command;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public abstract class TargetOtherPlayerCommand extends SimpleCommand {

	public TargetOtherPlayerCommand(@Nonnull final CrucialCraft cc, @Nonnull final Argument... args) {
		super(cc, args);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		if (!args.has("target")) { src.sendMessage(Text.of(TextColors.DARK_RED, "Error: ", TextColors.RED, "No target player found.")); return; }
		if (src.equals(args.get("target"))) { throw new CommandException("You cant target yourself."); }

		this.execute(src, args.<Player>get("target"), args);
	}

	@Override public String getPermission(@Nonnull final Arguments args) { return this.getPermission(); }
	public abstract String getPermission();
	public abstract void execute(final CommandSource src, final Player target, final Arguments args) throws CommandException;
}