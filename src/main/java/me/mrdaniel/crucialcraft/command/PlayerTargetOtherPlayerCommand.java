package me.mrdaniel.crucialcraft.command;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public abstract class PlayerTargetOtherPlayerCommand extends PlayerCommand {

	public PlayerTargetOtherPlayerCommand(@Nonnull final CrucialCraft cc, @Nonnull final Argument... args) {
		super(cc, args);
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		if (!args.has("target")) { throw new CommandException("No target player found."); }

		this.execute(src, args.<Player>get("target"), args);		
	}

	@Override public String getPermission(@Nonnull final Arguments args) { return this.getPermission(); }
	public abstract String getPermission();
	public abstract void execute(final Player src, final Player target, final Arguments args) throws CommandException;
}