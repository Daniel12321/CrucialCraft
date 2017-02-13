package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.Messages;

public abstract class PlayerCommand extends PermissionCommand {

	public PlayerCommand(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(@Nonnull final CommandSource src, @Nonnull final CommandContext args) {
		if (src instanceof Player) { this.execute((Player)src, args); }
		else { Messages.NOT_PLAYER.send(src); }
	}

	public abstract void execute(@Nonnull final Player target, @Nonnull final CommandContext args);
}