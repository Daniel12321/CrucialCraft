package me.mrdaniel.crucialcraft.commands.spawn;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;

public class CommandDelNewbieSpawn extends SimpleCommand {

	public CommandDelNewbieSpawn(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) {
		super.getCrucialCraft().getDataFile().setNewbieSpawn(null);
		src.sendMessage(Text.of(TextColors.GOLD, "You deleted the newbie spawn point."));
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.delnewbiespawn";
	}

	@Override
	public String getName() {
		return "NewbieSpawn Del";
	}
}