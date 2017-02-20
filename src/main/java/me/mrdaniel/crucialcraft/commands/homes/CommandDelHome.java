package me.mrdaniel.crucialcraft.commands.homes;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;

public class CommandDelHome extends PlayerCommand {

	public CommandDelHome(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.string("name"));
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		String name = args.get("name");

		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		file.getHome(name).orElseThrow(() -> new CommandException("No home with that name exists."));

		file.setHome(name, null);
		target.sendMessage(Text.of(TextColors.GOLD, "You deleted home ", TextColors.RED, name, TextColors.GOLD, "."));
	}

	@Override
	public String getName() {
		return "Home Del";
	}

	@Override
	public String getPermission() {
		return "cc.home.del";
	}
}