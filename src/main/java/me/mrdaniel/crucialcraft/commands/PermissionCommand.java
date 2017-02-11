package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CCObject;
import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.utils.Messages;

public abstract class PermissionCommand extends CCObject implements CommandExecutor {

	public PermissionCommand(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (!src.hasPermission(this.getPerm(args.<Player>getOne("other").isPresent()))) { Messages.NO_PERMISSION.send(src); }
		else { this.perform(src, args); }
		return CommandResult.success();
	}

	@Nonnull
	private String getPerm(final boolean other) {
		return other ? this.getPermission() + ".other" : this.getPermission();
	}

	public abstract void perform(@Nonnull final CommandSource src, @Nonnull final CommandContext args);
	public abstract String getPermission();
}