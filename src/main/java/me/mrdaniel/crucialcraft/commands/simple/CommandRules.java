package me.mrdaniel.crucialcraft.commands.simple;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;

public class CommandRules extends PermissionCommand {

	public CommandRules(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		src.sendMessages(super.getCrucialCraft().getConfig().getRules());
	}

	@Override
	public String getPermission() {
		return "cc.rules";
	}
}