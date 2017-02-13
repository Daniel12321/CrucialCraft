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

public class CommandFly extends TargetPlayerCommand {

	public CommandFly(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
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
	public boolean canTargetSelf() {
		return true;
	}
}