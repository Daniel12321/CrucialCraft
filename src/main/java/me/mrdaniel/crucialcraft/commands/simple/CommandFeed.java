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

public class CommandFeed extends PlayerCommand {

	public CommandFeed(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		target.offer(Keys.FOOD_LEVEL, 20);
		target.sendMessage(Text.of(TextColors.GOLD, "Your hunger was satisfied."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You satisfied ", TextColors.RED, target.getName(), TextColors.GOLD, "'s hunger.")));
	}

	@Override
	public String getPermission() {
		return "cc.feed";
	}
}