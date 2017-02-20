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
import me.mrdaniel.crucialcraft.command.TargetUserCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;

public class CommandMute extends TargetUserCommand {

	public CommandMute(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.user(cc, "target"));
	}

	@Override
	public void execute(final CommandSource src, final User target, final Arguments args) throws CommandException {
		Optional<Player> p = target.getPlayer();

		if (p.isPresent()) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			file.setMuted(true);
			p.get().sendMessage(Text.of(TextColors.GOLD, "You are now muted."));
		}
		else {
			PlayerFile file = super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId()).orElseThrow(() -> new CommandException("No user with that name exists."));
			file.setMuted(true);
		}
	}

	@Override
	public String getPermission() {
		return "cc.mute";
	}

	@Override
	public String getName() {
		return "Mute";
	}
}