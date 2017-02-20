package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetUserCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandSeen extends TargetUserCommand {

	public CommandSeen(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final CommandSource src, final User target, final Arguments args) throws CommandException {
		Optional<Player> p = target.getPlayer();

		if (p.isPresent()) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			src.sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, " has been online for ", TextUtils.getTimeFormat(System.currentTimeMillis() - file.getLastLogin()), "."));
		}
		else {
			PlayerFile file = super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId()).orElseThrow(() -> new CommandException("No user with that name exists."));
			src.sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, " was last seen ", TextColors.RED, TextUtils.getTimeFormat(System.currentTimeMillis() - file.getLastLogout()), TextColors.GOLD, " ago."));
		}
	}

	@Override
	public String getPermission() {
		return "cc.seen";
	}

	@Override
	public String getName() {
		return "Seen";
	}
}