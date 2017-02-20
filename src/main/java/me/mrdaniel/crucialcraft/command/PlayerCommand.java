package me.mrdaniel.crucialcraft.command;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public abstract class PlayerCommand extends SimpleCommand {

	public PlayerCommand(@Nonnull final CrucialCraft cc, @Nonnull final Argument... args) {
		super(cc, args);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		if (!(src instanceof Player)) { throw new CommandException("This command is for players only."); }
		this.execute(((Player)src), args);
	}

	@Override public String getPermission(@Nonnull final Arguments args) { return this.getPermission(); }
	public abstract String getPermission();
	public abstract void execute(@Nonnull final Player target, @Nonnull final Arguments args) throws CommandException;
}