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

public class CommandBurn extends TargetSelfOrOtherPlayerCommand {

	public CommandBurn(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) {
		int seconds = args.has("seconds") ? args.get("seconds") : 5;
		target.offer(Keys.FIRE_TICKS, seconds * 20);
		target.sendMessage(Text.of(TextColors.GOLD, "You were burned."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You burned ", TextColors.RED, target.getName(), TextColors.GOLD, " for ", TextColors.RED, seconds, TextColors.GOLD, " seconds.")));
	}

	@Override
	public String getPermission() {
		return "cc.burn";
	}

	@Override
	public String getName() {
		return "Burn";
	}
}