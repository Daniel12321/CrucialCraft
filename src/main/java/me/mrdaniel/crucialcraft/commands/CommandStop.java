package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandStop extends SimpleCommand {

	public CommandStop(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.remaining("message")));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		if (args.has("reason")) { super.getCrucialCraft().getGame().getServer().shutdown(TextUtils.toText(args.get("reason"))); }
		else { super.getCrucialCraft().getGame().getServer().shutdown(); }
	}

	@Override
	public String getName() {
		return "Stop";
	}

	@Override
	public String getPermission(final Arguments args) {
		return "cc.stop";
	}
}