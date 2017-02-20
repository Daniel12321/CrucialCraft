package me.mrdaniel.crucialcraft.commands.warps;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandDelWarp extends SimpleCommand {

	public CommandDelWarp(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.string("name"));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		String name = args.get("name");
		if (!super.getCrucialCraft().getDataFile().getWarp(name).isPresent()) {	throw new CommandException("No warp with that name exists."); }

		super.getCrucialCraft().getDataFile().setWarp(name, null);
		src.sendMessage(Text.of(TextColors.GOLD, "You deleted warp ", TextColors.RED, name, TextColors.GOLD, "."));
	}

	@Override
	public String getName() {
		return "Warp Del";
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.warp.del";
	}
}