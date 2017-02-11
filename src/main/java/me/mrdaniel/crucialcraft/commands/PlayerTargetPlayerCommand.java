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

//	@Override
//	public void perform(final CommandSource src, CommandContext args) {
//		if (src instanceof Player) {
//			Player source = (Player) src;
//			Player target = (args.getOne("target").isPresent()) ? args.<Player>getOne("target").get() : source;
//			this.perform(target, source, args);
//		}
//		else { Messages.NOT_PLAYER.send(src); }
//	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		if (!(src instanceof Player)) { Messages.NOT_PLAYER.send(src); return; }

		Player source = (Player) src;
		Player target = args.<Player>getOne("target").get();

		if (source.equals(target) && !this.canTargetSelf()) { Messages.NO_TARGET_YOURSELF.send(source); return; }

		this.execute(target, source, args);
	}

	public void execute(final Player target, final CommandSource src, final CommandContext args) {
		if (!(src instanceof Player)) { Messages.NOT_PLAYER.send(src); return; }
		this.execute(target, (Player)src, args) ;
	}

	public abstract void execute(@Nonnull final Player target, @Nonnull final Player src, @Nonnull final CommandContext args);
	public abstract boolean canTargetSelf();
}