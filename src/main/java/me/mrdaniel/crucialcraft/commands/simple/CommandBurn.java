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

public class CommandBurn extends TargetPlayerCommand {

	public CommandBurn(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		int seconds = args.<Integer>getOne("seconds").orElse(5);
		target.offer(Keys.FIRE_TICKS, seconds * 20);
		target.sendMessage(Text.of(TextColors.GOLD, "You were burned."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You burned ", TextColors.RED, target.getName(), TextColors.GOLD, " for ", TextColors.RED, seconds, TextColors.GOLD, " seconds.")));
	}

	@Override
	public String getPermission() {
		return "cc.burn";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}