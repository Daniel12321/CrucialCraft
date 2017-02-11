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
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandSpeed extends PlayerCommand {

	public CommandSpeed(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		double value = ServerUtils.between(args.<Double>getOne("speed").get(), 0.0, 10.0);
		if (target.get(Keys.IS_FLYING).orElse(false)) {
			target.offer(Keys.FLYING_SPEED, value);
			target.sendMessage(Text.of(TextColors.GOLD, "You flying speed is now ", TextColors.RED, value, TextColors.GOLD, "."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s flying speed to ", TextColors.RED, value, TextColors.GOLD, ".")));
		}
		else {
			target.offer(Keys.WALKING_SPEED, value);
			target.sendMessage(Text.of(TextColors.GOLD, "You walking speed is now ", TextColors.RED, value, TextColors.GOLD, "."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s walking speed to ", TextColors.RED, value, TextColors.GOLD, ".")));
		}
	}

	@Override
	public String getPermission() {
		return "cc.speed";
	}
}