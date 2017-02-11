package me.mrdaniel.crucialcraft.commands.simple;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandBroadcast extends PermissionCommand {

	public CommandBroadcast(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		ServerUtils.broadcast(super.getCrucialCraft().getGame().getServer(), super.getCrucialCraft().getConfig().getBroadcastMessage(args.<String>getOne("message").get()));
	}

	@Override
	public String getPermission() {
		return "cc.broadcast";
	}
}