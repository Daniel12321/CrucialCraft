package me.mrdaniel.crucialcraft.commands.jail;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandDelJail extends SimpleCommand {

	public CommandDelJail(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.string("name"));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		String name = args.get("name");
		super.getCrucialCraft().getDataFile().getJail(name).orElseThrow(() -> new CommandException("No jail with that name exists."));

		super.getCrucialCraft().getDataFile().setJail(name, null);
		src.sendMessage(Text.of(TextColors.GOLD, "You deleted jail ", TextColors.RED, name, TextColors.GOLD, "."));
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.jail.del";
	}

	@Override
	public String getName() {
		return "Jail Del";
	}
}