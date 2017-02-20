package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetOtherPlayerCommand;

public class CommandKill extends TargetOtherPlayerCommand {

	public CommandKill(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.player(cc, "target"));
	}

	@Override
	public void execute(final CommandSource src, final Player target, final Arguments args) {
		target.offer(Keys.HEALTH, 0.0);
		target.sendMessage(Text.of(TextColors.GOLD, "You were killed."));
		src.sendMessage(Text.of(TextColors.GOLD, "You killed ", TextColors.RED, target.getName(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.kill";
	}

	@Override
	public String getName() {
		return "Kill";
	}
}