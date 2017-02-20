package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandBroadcast extends SimpleCommand {

	public CommandBroadcast(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.remaining("message"));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		ServerUtils.broadcast(super.getCrucialCraft().getGame().getServer(), super.getCrucialCraft().getConfig().getBroadcastMessage(args.<String>get("message")));
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.broadcast";
	}

	@Override
	public String getName() {
		return "Broadcast";
	}
}