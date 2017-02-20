package me.mrdaniel.crucialcraft.command;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.User;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public abstract class TargetSelfOrUserCommand extends SimpleCommand {

	public TargetSelfOrUserCommand(@Nonnull final CrucialCraft cc, @Nonnull final Argument... args) {
		super(cc, args);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		if (!args.has("target") || !(src instanceof User)) { throw new CommandException("You must specify a user."); }
		User target = args.has("target") ? args.get("target") : (User)src;

		if (target.equals(src)) { this.execute(Optional.empty(), target, args); }
		else { this.execute(Optional.of(src), target, args); }
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return this.getPermission() + (args.has("target") ? ".other" : ".self");
	}

	public abstract String getPermission();
	public abstract void execute(final Optional<CommandSource> src, final User target, final Arguments args) throws CommandException;
}