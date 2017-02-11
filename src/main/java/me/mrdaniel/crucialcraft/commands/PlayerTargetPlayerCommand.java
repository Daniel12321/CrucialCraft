package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.Messages;

public abstract class PlayerTargetPlayerCommand extends PermissionCommand {

	public PlayerTargetPlayerCommand(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		if (!(src instanceof Player)) { Messages.NOT_PLAYER.send(src); return; }
		Player source = (Player) src;

		if (args.<Player>getOne("target").isPresent()) {
			Player target = args.<Player>getOne("target").get();
			if (target.equals(source) && !this.canTargetSelf()) { Messages.NO_TARGET_YOURSELF.send(source); }
			else { this.execute(target, source, args); }
		}
		else if (this.canTargetSelf()) { this.execute(source, source, args); }
		else { Messages.NO_TARGET_YOURSELF.send(src); }
	}

	public abstract void execute(@Nonnull final Player target, @Nonnull final Player src, @Nonnull final CommandContext args);
	public abstract boolean canTargetSelf();
}