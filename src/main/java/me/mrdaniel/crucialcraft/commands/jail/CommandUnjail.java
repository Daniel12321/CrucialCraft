package me.mrdaniel.crucialcraft.commands.jail;

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
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandUnjail extends TargetUserCommand {

	public CommandUnjail(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.user(cc, "target"));
	}

	@Override
	public void execute(final CommandSource src, final User target, final Arguments args) throws CommandException {
		Optional<Player> p = target.getPlayer();

		if (p.isPresent()) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			if (!file.isJailed()) { throw new CommandException("This user is not jailed."); }
			file.setJailed(false);

			Optional<Teleport> spawn = super.getCrucialCraft().getDataFile().getSpawn();
			if (!(spawn.isPresent() && spawn.get().teleport(super.getCrucialCraft(), p.get(), Text.of(TextColors.GOLD, "You are no longer jailed."), true))) { p.get().setLocation(p.get().getWorld().getSpawnLocation()); }
			src.sendMessage(Text.of(TextColors.GOLD, "You unjailed ", TextColors.RED, target.getName(), TextColors.GOLD, "."));
		}
		else {
			Optional<PlayerFile> file = super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId());
			if (!file.isPresent()) { throw new CommandException("This user is not jailed."); }

			file.get().setJailed(false);
			src.sendMessage(Text.of(TextColors.GOLD, "You unjailed ", TextColors.RED, target.getName(), TextColors.GOLD, "."));
		}
	}

	@Override
	public String getPermission() {
		return "cc.jail.toggle";
	}

	@Override
	public String getName() {
		return "Unjail";
	}
}