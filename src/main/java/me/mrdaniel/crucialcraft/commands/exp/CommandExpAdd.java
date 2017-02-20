package me.mrdaniel.crucialcraft.commands.exp;

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
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandExpAdd extends TargetSelfOrOtherPlayerCommand {

	public CommandExpAdd(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")), Argument.integer("exp"));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) throws CommandException {
		int value = args.<Integer>get("exp") + target.get(Keys.TOTAL_EXPERIENCE).orElse(0);
		target.offer(Keys.TOTAL_EXPERIENCE, value);
		target.sendMessage(Text.of(TextColors.GOLD, "Your exp was set to ", TextColors.RED, value, TextColors.GOLD, "."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s exp to ", TextColors.RED, value, TextColors.GOLD, ".")));
	}

	@Override
	public String getName() {
		return "Exp Add";
	}

	@Override
	public String getPermission() {
		return "cc.exp.add";
	}
}