package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandSuicide extends PlayerCommand {

	public CommandSuicide(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Arguments args) {
		target.offer(Keys.HEALTH, 0.0);
		ServerUtils.broadcast(super.getCrucialCraft().getGame().getServer(), Text.of(TextColors.RED, target.getName(), TextColors.GOLD, " committed suicide."));
	}

	@Override
	public String getPermission() {
		return "cc.suicide";
	}

	@Override
	public String getName() {
		return "Suicide";
	}
}