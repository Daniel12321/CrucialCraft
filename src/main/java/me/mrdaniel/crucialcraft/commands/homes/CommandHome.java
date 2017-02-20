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
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandHome extends PlayerCommand {

	public CommandHome(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.string("name")));
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		if (!args.has("name")) { super.getCrucialCraft().getGame().getCommandManager().process(src, "home list"); return; }
		String name = args.get("name");
		Teleport home = super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getHome(name).orElseThrow(() -> new CommandException("No home with that name exists."));

		if (!home.teleport(super.getCrucialCraft(), src, Text.of(TextColors.GOLD, "You were teleported to your home ", TextColors.RED, name, TextColors.GOLD, "."), false)) {
			throw new CommandException("This location doesnt exist anymore.");
		}
	}

	@Override
	public String getName() {
		return "Home";
	}

	@Override
	public String getPermission() {
		return "cc.home";
	}
}