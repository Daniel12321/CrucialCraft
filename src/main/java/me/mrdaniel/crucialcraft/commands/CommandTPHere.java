package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerTargetOtherPlayerCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandTPHere extends PlayerTargetOtherPlayerCommand {

	public CommandTPHere(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.player(cc, "target"));
	}

	@Override
	public void execute(final Player src, final Player target, final Arguments args) {
		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		file.setLastLocation(new Teleport(target.getLocation(), target.getHeadRotation()));

		target.setLocationAndRotation(src.getLocation(), src.getHeadRotation());
		target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, src.getName(), TextColors.GOLD, "'s location."));
		src.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to your location."));
	}

	@Override
	public String getPermission() {
		return "cc.tphere";
	}

	@Override
	public String getName() {
		return "TPHere";
	}
}