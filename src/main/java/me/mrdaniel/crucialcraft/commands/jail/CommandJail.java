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

public class CommandJail extends TargetUserCommand {

	public CommandJail(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final User target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<Player> p = target.getPlayer();

		String name = args.<String>getOne("name").get();
		Optional<Teleport> jail = super.getCrucialCraft().getDataFile().getJail(name);
		if (!jail.isPresent()) { Messages.NO_SUCH_JAIL.send(src.orElse(p.get())); return; }
		Teleport teleport = jail.get();

		if (p.isPresent()) {
			teleport.teleport(super.getCrucialCraft(), p.get());
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			file.setJailed(true);

			p.get().sendMessage(Text.of(TextColors.GOLD, "You were jailed."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You jailed ", TextColors.RED, target.getName(), TextColors.GOLD, " in jail ", TextColors.RED, name, TextColors.GOLD, ".")));
		}
		else {
			Optional<PlayerFile> file = super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId());
			if (!file.isPresent()) { Messages.NO_SUCH_USER.send(src.get()); return; }
			file.get().setJailed(true);
		}
	}

	@Override
	public String getPermission() {
		return "cc.jail.toggle";
	}

	@Override
	public boolean canTargetSelf() {
		return false;
	}
}