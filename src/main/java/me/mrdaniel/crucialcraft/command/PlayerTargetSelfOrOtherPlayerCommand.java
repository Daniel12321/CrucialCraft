package me.mrdaniel.crucialcraft.command;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public abstract class PlayerTargetSelfOrOtherPlayerCommand extends PlayerCommand {

	public PlayerTargetSelfOrOtherPlayerCommand(@Nonnull final CrucialCraft cc, @Nonnull final Argument... args) {
		super(cc, args);
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		if (!args.has("target")) { throw new CommandException("No target player found."); }
		Player target = args.get("target");

		this.execute(src.equals(target) ? Optional.empty() : Optional.of(src), target, args);
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return this.getPermission() + (args.has("target") ? ".other" : ".self");
	}

	public abstract String getPermission();
	public abstract void execute(final Optional<Player> src, final Player target, final Arguments args) throws CommandException;
}