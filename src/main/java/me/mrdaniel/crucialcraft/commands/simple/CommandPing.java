package me.mrdaniel.crucialcraft.commands.simple;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;

public class CommandPing extends PlayerCommand {

	public CommandPing(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		target.sendMessage(Text.of(TextColors.GOLD, "Your ping is ", TextColors.RED, target.getConnection().getLatency(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.ping";
	}
}