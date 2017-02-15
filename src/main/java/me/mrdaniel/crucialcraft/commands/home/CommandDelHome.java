package me.mrdaniel.crucialcraft.commands.home;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandDelHome extends PlayerCommand {

	public CommandDelHome(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		String name = args.<String>getOne("name").get();

		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		Optional<Teleport> home = file.getHome(name);
		if (!home.isPresent()) { Messages.NO_SUCH_HOME.send(target); return; }

		file.setHome(name, null);
		target.sendMessage(Text.of(TextColors.GOLD, "You deleted home ", TextColors.RED, name, TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.home.del";
	}
}