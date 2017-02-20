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

public class CommandExp extends TargetSelfOrOtherPlayerCommand {

	public CommandExp(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) throws CommandException {
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Experience of ", TextColors.RED, super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).getNick().orElse(Text.of(target.getName())), TextColors.GOLD, "."));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Level: ", TextColors.RED, target.get(Keys.EXPERIENCE_LEVEL).orElse(0)));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Exp since level: ", TextColors.RED, target.get(Keys.EXPERIENCE_SINCE_LEVEL).orElse(0)));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Total Exp: ", TextColors.RED, target.get(Keys.TOTAL_EXPERIENCE).orElse(0)));
	}

	@Override
	public String getName() {
		return "Exp";
	}

	@Override
	public String getPermission() {
		return "cc.exp.get";
	}
}