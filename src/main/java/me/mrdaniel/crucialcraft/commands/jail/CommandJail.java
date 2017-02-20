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
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandJail extends TargetUserCommand {

	public CommandJail(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.user(cc, "target"));
	}

	@Override
	public void execute(final CommandSource src, final User target, final Arguments args) throws CommandException {
		Optional<Player> p = target.getPlayer();

		String name = args.get("name");
		Teleport jail = super.getCrucialCraft().getDataFile().getJail(name).orElseThrow(() -> new CommandException("No jail with that name exists."));

		if (p.isPresent()) {
			jail.teleport(super.getCrucialCraft(), p.get(), Text.of(TextColors.GOLD, "You were jailed."), true);
			super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).setJailed(true);

			src.sendMessage(Text.of(TextColors.GOLD, "You jailed ", TextColors.RED, target.getName(), TextColors.GOLD, " in jail ", TextColors.RED, name, TextColors.GOLD, "."));
		}
		else {
			super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId()).orElseThrow(() -> new CommandException("No user with that name exists.")).setJailed(true);
		}
	}

	@Override
	public String getPermission() {
		return "cc.jail.toggle";
	}

	@Override
	public String getName() {
		return "Jail";
	}
}