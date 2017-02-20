package me.mrdaniel.crucialcraft.commands.spawn;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandSetNewbieSpawn extends PlayerCommand {

	public CommandSetNewbieSpawn(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) {
		super.getCrucialCraft().getDataFile().setNewbieSpawn(new Teleport(src.getLocation(), src.getHeadRotation()));
		src.sendMessage(Text.of(TextColors.GOLD, "You set the newbie spawn point to your location."));
	}

	@Override
	public String getPermission() {
		return "cc.setnewbiespawn";
	}

	@Override
	public String getName() {
		return "NewbieSpawn Set";
	}
}