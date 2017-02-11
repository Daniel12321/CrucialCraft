package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.data.Teleport;

public class CommandSetSpawn extends PlayerCommand {

	public CommandSetSpawn(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		super.getCrucialCraft().getDataFile().setSpawn(new Teleport(target.getLocation(), target.getHeadRotation()));
		target.sendMessage(Text.of(TextColors.GOLD, "You set the spawn point to your location."));
	}

	@Override
	public String getPermission() {
		return "cc.setspawn";
	}
}