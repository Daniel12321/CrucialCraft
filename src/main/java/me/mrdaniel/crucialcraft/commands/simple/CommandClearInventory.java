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

public class CommandClearInventory extends PlayerCommand {

	public CommandClearInventory(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		target.getInventory().clear();
		target.sendMessage(Text.of(TextColors.GOLD, "Your inventory was cleared."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You cleared ", TextColors.RED, target.getName(), "'s inventory.")));
	}

	@Override
	public String getPermission() {
		return "cc.clearinventory";
	}
}