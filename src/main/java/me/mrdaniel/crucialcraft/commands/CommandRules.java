package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;

public class CommandRules extends SimpleCommand {

	public CommandRules(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) {
		src.sendMessages(super.getCrucialCraft().getConfig().getRules());
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.rules";
	}


	@Override
	public String getName() {
		return "Rules";
	}
}