package me.mrdaniel.crucialcraft.commands.jail;

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
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandUnjail extends TargetUserCommand {

	public CommandUnjail(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final User target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<Player> p = target.getPlayer();

		if (p.isPresent()) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			if (!file.isJailed()) { Messages.IS_NOT_JAILED.send(src.orElse(p.get())); return; }
			file.setJailed(false);

			Optional<Teleport> spawn = super.getCrucialCraft().getDataFile().getSpawn();
			if (!(spawn.isPresent() && spawn.get().teleport(super.getCrucialCraft(), p.get(), Text.of(TextColors.GOLD, "You are no longer jailed."), true))) { p.get().setLocation(p.get().getWorld().getSpawnLocation()); }
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You unjailed ", TextColors.RED, target.getName(), TextColors.GOLD, ".")));
		}
		else {
			Optional<PlayerFile> file = super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId());
			if (!file.isPresent()) { Messages.NO_SUCH_USER.send(src.get()); return; }

			file.get().setJailed(false);
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You unjailed ", TextColors.RED, target.getName(), TextColors.GOLD, ".")));
		}
	}

	@Override
	public String getPermission() {
		return "cc.jail.toggle";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}