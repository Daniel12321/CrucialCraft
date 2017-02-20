package me.mrdaniel.crucialcraft.commands.warps;

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

public class CommandSetWarp extends PlayerCommand {

	public CommandSetWarp(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.string("name"));
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		String name = args.get("name");
		Teleport teleport = new Teleport(target.getLocation(), target.getHeadRotation());

		super.getCrucialCraft().getDataFile().setWarp(name, teleport);
		target.sendMessage(Text.of(TextColors.GOLD, "You set warp ", TextColors.RED, name, TextColors.GOLD, " to your location."));
	}

	@Override
	public String getName() {
		return "Warp Set";
	}

	@Override
	public String getPermission() {
		return "cc.warp.set";
	}
}