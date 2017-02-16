package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.User;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.Messages;

public abstract class TargetUserCommand extends PermissionCommand {

	public TargetUserCommand(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		Optional<User> u = args.<User>getOne("target");
		if (u.isPresent()) {
			User target = u.get();
			if (target.equals(src)) {
				if (this.canTargetSelf()) { this.execute(target, Optional.empty(), args); }
				else { Messages.NO_TARGET_YOURSELF.send(src); }
			}
			else { this.execute(target, Optional.of(src), args); }
		}
		else if (this.canTargetSelf() && src instanceof User) { this.execute((User)src, Optional.empty(), args); }
		else { Messages.NO_TARGET_YOURSELF.send(src); }
	}

	public abstract void execute(@Nonnull final User target, @Nonnull final Optional<CommandSource> src, @Nonnull final CommandContext args);
	public abstract boolean canTargetSelf();
}