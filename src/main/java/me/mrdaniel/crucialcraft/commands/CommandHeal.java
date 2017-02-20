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

public class CommandHeal extends TargetSelfOrOtherPlayerCommand {

	public CommandHeal(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) {
		target.offer(Keys.HEALTH, target.get(Keys.MAX_HEALTH).orElse(20.0));
		target.sendMessage(Text.of(TextColors.GOLD, "You were fully healed."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You fully healed ", TextColors.RED, target.getName(), TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.heal";
	}

	@Override
	public String getName() {
		return "Heal";
	}
}