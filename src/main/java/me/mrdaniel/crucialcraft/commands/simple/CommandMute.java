package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetUserCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandMute extends TargetUserCommand {

	public CommandMute(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final User target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<Player> p = target.getPlayer();

		if (p.isPresent()) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			file.setMuted(true);
			p.get().sendMessage(Text.of(TextColors.GOLD, "You are now muted."));
		}
		else {
			Optional<PlayerFile> file = super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId());
			if (!file.isPresent()) { Messages.NO_SUCH_USER.send(src.get()); return; }
			file.get().setMuted(true);
		}
	}

	@Override
	public String getPermission() {
		return "cc.mute";
	}

	@Override
	public boolean canTargetSelf() {
		return false;
	}
}