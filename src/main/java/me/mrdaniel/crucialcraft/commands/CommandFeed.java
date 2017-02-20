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

public class CommandFeed extends TargetSelfOrOtherPlayerCommand {

	public CommandFeed(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) {
		target.offer(Keys.FOOD_LEVEL, 20);
		target.sendMessage(Text.of(TextColors.GOLD, "Your hunger was satisfied."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You satisfied ", TextColors.RED, target.getName(), TextColors.GOLD, "'s hunger.")));
	}

	@Override
	public String getPermission() {
		return "cc.feed";
	}

	@Override
	public String getName() {
		return "Feed";
	}
}