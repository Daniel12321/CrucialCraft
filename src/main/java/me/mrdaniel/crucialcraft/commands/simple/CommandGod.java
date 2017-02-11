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
import me.mrdaniel.crucialcraft.commands.PlayerCommand;

public class CommandGod extends PlayerCommand {

	public CommandGod(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		if (target.get(Keys.INVULNERABILITY_TICKS).orElse(0) > 1000) {
			target.offer(Keys.INVULNERABILITY_TICKS, 0);
			target.sendMessage(Text.of(TextColors.GOLD, "You are no longer invulnerable."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, " is no longer invulnerable.")));
		}
		else {
			target.offer(Keys.INVULNERABILITY_TICKS, Integer.MAX_VALUE);
			target.sendMessage(Text.of(TextColors.GOLD, "You are now invulnerable."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, " is now invulnerable.")));
		}
	}

	@Override
	public String getPermission() {
		return "cc.god";
	}
}