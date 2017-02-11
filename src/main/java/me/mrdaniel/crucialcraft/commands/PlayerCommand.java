package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

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
		if (args.<Player>getOne("other").isPresent()) {
			Player target = args.<Player>getOne("other").get();

			if (target.equals(src)) { this.perform(target, Optional.empty(), args); }
			else { this.perform(target, Optional.of(src), args); }
		}
		else if (src instanceof Player) { this.perform((Player)src, Optional.empty(), args); }
		else { Messages.NOT_PLAYER.send(src); }
	}

	public abstract void perform(@Nonnull final Player target, @Nonnull final Optional<CommandSource> src, @Nonnull final CommandContext args);
}