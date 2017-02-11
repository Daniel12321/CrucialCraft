package me.mrdaniel.crucialcraft.commands.simple;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;

public class CommandDelNewbieSpawn extends PermissionCommand {

	public CommandDelNewbieSpawn(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		super.getCrucialCraft().getDataFile().setNewbieSpawn(null);
		src.sendMessage(Text.of(TextColors.GOLD, "You deleted the newbie spawn point."));
	}

	@Override
	public String getPermission() {
		return "cc.delnewbiespawn";
	}
}