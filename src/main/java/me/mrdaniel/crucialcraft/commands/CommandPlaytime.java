package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrUserCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandPlaytime extends TargetSelfOrUserCommand {

	public CommandPlaytime(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.user(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final User target, final Arguments args) throws CommandException {
		Optional<Player> p = target.getPlayer();
		if (p.isPresent()) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			src.orElse(p.get()).sendMessage(Text.of(TextColors.RED, p.get().getName(), TextColors.GOLD, "'s total playtime is ", TextColors.RED, TextUtils.getTimeFormat(file.getCurrentPlaytime()), TextColors.GOLD, "."));
		}
		else {
			PlayerFile file = super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId()).orElseThrow(() -> new CommandException("No user with that name exists."));
			src.orElse(p.get()).sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, "'s total playtime is ", TextColors.RED, TextUtils.getTimeFormat(file.getCurrentPlaytime()), TextColors.GOLD, "."));
		}
	}

	@Override
	public String getPermission() {
		return "cc.playtime";
	}

	@Override
	public String getName() {
		return "Playtime";
	}
}