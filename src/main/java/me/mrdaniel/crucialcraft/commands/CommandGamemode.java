package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;

public class CommandGamemode extends TargetSelfOrOtherPlayerCommand {

	public CommandGamemode(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")), Argument.gamemode("gamemode"));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) {
		GameMode gm = args.get("gamemode");

		target.offer(Keys.GAME_MODE, gm);
		target.sendMessage(Text.of(TextColors.GOLD, "Your gamemode was set to ", TextColors.RED, gm.getName(), TextColors.GOLD, "."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s gamemode to ", TextColors.RED, gm.getName(), TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.gamemode";
	}

	@Override
	public String getName() {
		return "Gamemode";
	}
}