package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;

public class CommandKill extends TargetPlayerCommand {

	public CommandKill(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		target.offer(Keys.HEALTH, 0.0);
		target.sendMessage(Text.of(TextColors.GOLD, "You were killed."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You killed ", TextColors.RED, target.getName(), TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.kill";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}