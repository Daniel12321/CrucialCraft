package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandSpeed extends TargetSelfOrOtherPlayerCommand {

	public CommandSpeed(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")), Argument.doubleNum("speed"));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) {
		double value = ServerUtils.between(args.get("speed"), 0.0, 10.0);
		if (target.get(Keys.IS_FLYING).orElse(false)) {
			target.offer(Keys.FLYING_SPEED, value / 10);
			target.sendMessage(Text.of(TextColors.GOLD, "You flying speed is now ", TextColors.RED, value, TextColors.GOLD, "."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s flying speed to ", TextColors.RED, value, TextColors.GOLD, ".")));
		}
		else {
			target.offer(Keys.WALKING_SPEED, value / 10);
			target.sendMessage(Text.of(TextColors.GOLD, "You walking speed is now ", TextColors.RED, value, TextColors.GOLD, "."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s walking speed to ", TextColors.RED, value, TextColors.GOLD, ".")));
		}
	}

	@Override
	public String getPermission() {
		return "cc.speed";
	}

	@Override
	public String getName() {
		return "Speed";
	}
}