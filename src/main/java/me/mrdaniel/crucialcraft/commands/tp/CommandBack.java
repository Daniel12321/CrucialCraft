package me.mrdaniel.crucialcraft.commands.tp;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandBack extends PlayerCommand {

	public CommandBack(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		if (!file.getLastLocation().isPresent()) { Messages.NO_LAST_LOCATION.send(target); return; }
		Teleport tp = file.getLastLocation().get();

		if (!tp.teleport(super.getCrucialCraft(), target, Text.of(TextColors.GOLD, "You were teleported back to your last location."), false)) { Messages.TELEPORT_DOESNT_EXIST.send(target); }
	}

	@Override
	public String getPermission() {
		return "cc.back";
	}
}