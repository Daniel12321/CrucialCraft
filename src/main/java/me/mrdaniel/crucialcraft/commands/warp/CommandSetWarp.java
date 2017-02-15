package me.mrdaniel.crucialcraft.commands.warp;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandSetWarp extends PlayerCommand {

	public CommandSetWarp(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		String name = args.<String>getOne("name").get();
		Teleport teleport = new Teleport(target.getLocation(), target.getHeadRotation());

		super.getCrucialCraft().getDataFile().setWarp(name, teleport);
		target.sendMessage(Text.of(TextColors.GOLD, "You set warp ", TextColors.RED, name, TextColors.GOLD, " to your location."));
	}

	@Override
	public String getPermission() {
		return "cc.warp.set";
	}
}