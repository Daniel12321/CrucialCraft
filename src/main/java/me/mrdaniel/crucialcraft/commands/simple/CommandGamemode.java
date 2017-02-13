package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandGamemode extends TargetPlayerCommand {

	public CommandGamemode(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		GameMode gm;

		String str = args.<String>getOne("gamemode").get().toLowerCase();

		if (str.equals("0") || str.startsWith("su")) { gm = GameModes.SURVIVAL; }
		else if (str.equals("1") || str.startsWith("c")) { gm = GameModes.CREATIVE; }
		else if (str.equals("2") || str.startsWith("a")) { gm = GameModes.ADVENTURE; }
		else if (str.equals("3") || str.startsWith("sp")) { gm = GameModes.SPECTATOR; }
		else { Messages.NO_SUCH_GAMEMODE.send(src.orElse(target)); return; }

		target.offer(Keys.GAME_MODE, gm);
		target.sendMessage(Text.of(TextColors.GOLD, "Your gamemode was set to ", TextColors.RED, gm.getName(), TextColors.GOLD, "."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s gamemode to ", TextColors.RED, gm.getName(), TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.gamemode";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}