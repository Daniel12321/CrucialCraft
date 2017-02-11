package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.Messages;

public abstract class TargetPlayerCommand extends PermissionCommand {

	public TargetPlayerCommand(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		// Player target; // = args.<Player>getOne("target").get();

		if (args.<Player>getOne("target").isPresent()) {
			Player target = args.<Player>getOne("target").get();
			if (target.equals(src)) {
				if (this.canTargetSelf()) { this.execute(target, Optional.empty(), args); }
				else { Messages.NO_TARGET_YOURSELF.send(src); }
			}
			else { this.execute(target, Optional.of(src), args); }
		}
		else if (this.canTargetSelf() && src instanceof Player) { this.execute((Player)src, Optional.empty(), args); }
		else { Messages.NO_TARGET_YOURSELF.send(src); }
	}

	public abstract void execute(@Nonnull final Player target, @Nonnull final Optional<CommandSource> src, @Nonnull final CommandContext args);
	public abstract boolean canTargetSelf();
}