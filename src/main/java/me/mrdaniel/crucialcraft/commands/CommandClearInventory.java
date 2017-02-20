package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;

public class CommandClearInventory extends TargetSelfOrOtherPlayerCommand {

	public CommandClearInventory(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) {
		target.getInventory().clear();
		target.sendMessage(Text.of(TextColors.GOLD, "Your inventory was cleared."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You cleared ", TextColors.RED, target.getName(), "'s inventory.")));
	}

	@Override
	public String getPermission() {
		return "cc.clearinventory";
	}

	@Override
	public String getName() {
		return "ClearInventory";
	}
}