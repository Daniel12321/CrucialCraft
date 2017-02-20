package me.mrdaniel.crucialcraft.commands.spawn;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandSetSpawn extends PlayerCommand {

	public CommandSetSpawn(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Arguments args) {
		super.getCrucialCraft().getDataFile().setSpawn(new Teleport(target.getLocation(), target.getHeadRotation()));
		target.sendMessage(Text.of(TextColors.GOLD, "You set the spawn point to your location."));
	}

	@Override
	public String getPermission() {
		return "cc.setspawn";
	}

	@Override
	public String getName() {
		return "Spawn Set";
	}
}