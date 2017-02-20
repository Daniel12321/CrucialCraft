package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;

public class CommandWhois extends TargetSelfOrOtherPlayerCommand {

	public CommandWhois(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) throws CommandException {
		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());

		src.orElse(target).sendMessage(Text.of(TextColors.RED, target.getName(), ":"));
		file.getNick().ifPresent(nick -> src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Nickname: ", TextColors.GOLD, nick)));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Health: ", TextColors.RED, target.get(Keys.HEALTH).orElse(0.0).intValue(), " / ", target.get(Keys.MAX_HEALTH).orElse(20.0).intValue()));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Hunger: ", TextColors.GOLD, target.get(Keys.FOOD_LEVEL).orElse(0), " / 20"));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Exp: ", TextColors.RED, target.get(Keys.TOTAL_EXPERIENCE).orElse(0), " (Level ", target.get(Keys.EXPERIENCE_LEVEL).orElse(0), ")"));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Location: ", TextColors.RED, target.getWorld().getName(), ", ", target.getLocation().getBlockX(), ", ", target.getLocation().getBlockY(), ", ", target.getLocation().getBlockZ()));
//		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Money: ", super.getCrucialCraft().getEconomy().getOrCreateAccount(target.getUniqueId()).get().get));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Gamemode: ", TextColors.RED, target.get(Keys.GAME_MODE).orElse(GameModes.SURVIVAL).getName()));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "God: ", TextColors.RED, target.get(Keys.INVULNERABILITY_TICKS).orElse(0) > 1000));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Flying: ", TextColors.GOLD, target.get(Keys.CAN_FLY).orElse(false)));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "OP: ", TextColors.RED, target.hasPermission("random.permission")));
//		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "AFK: "));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Jailed: ", TextColors.RED, file.isJailed()));
		src.orElse(target).sendMessage(Text.of(TextColors.GOLD, "Muted: ", TextColors.RED, file.isMuted()));
	}

	@Override
	public String getPermission() {
		return "cc.whois";
	}

	@Override
	public String getName() {
		return "Whois";
	}
}
