package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerTargetSelfOrOtherPlayerCommand;

public class CommandPing extends PlayerTargetSelfOrOtherPlayerCommand {

	public CommandPing(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Optional<Player> src, final Player target, final Arguments args) {
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Your ping is ", TextColors.RED, target.getConnection().getLatency(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.ping";
	}

	@Override
	public String getName() {
		return "Ping";
	}
}