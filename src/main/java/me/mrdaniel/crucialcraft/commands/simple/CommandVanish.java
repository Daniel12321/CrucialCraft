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

public class CommandVanish extends PlayerCommand {

	public CommandVanish(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		if (target.get(Keys.INVISIBLE).orElse(false)) {
			target.offer(Keys.VANISH, false);
			target.offer(Keys.INVISIBLE, false);
			target.sendMessage(Text.of(TextColors.GOLD, "You are no longer invisible."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.RED, target.getName(), " is no longer invisible.")));
		}
		else {
			target.offer(Keys.VANISH, true);
			target.offer(Keys.INVISIBLE, true);
			target.sendMessage(Text.of(TextColors.GOLD, "You are now invisible."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.RED, target.getName(), " is now invisible.")));
		}
	}

	@Override
	public String getPermission() {
		return "cc.vanish";
	}
}