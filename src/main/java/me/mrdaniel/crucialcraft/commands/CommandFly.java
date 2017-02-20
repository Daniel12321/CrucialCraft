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

public class CommandFly extends TargetSelfOrOtherPlayerCommand {

	public CommandFly(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) {
		if (target.get(Keys.CAN_FLY).orElse(false)) {
			target.offer(Keys.CAN_FLY, false);
			target.offer(Keys.IS_FLYING, false);
			target.sendMessage(Text.of(TextColors.GOLD, "You can no longer fly."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, " can no longer fly.")));
		}
		else {
			target.offer(Keys.CAN_FLY, true);
			target.sendMessage(Text.of(TextColors.GOLD, "You can now fly."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, " can now fly.")));
		}
	}

	@Override
	public String getPermission() {
		return "cc.fly";
	}

	@Override
	public String getName() {
		return "Fly";
	}
}