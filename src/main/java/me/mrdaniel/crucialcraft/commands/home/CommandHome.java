package me.mrdaniel.crucialcraft.commands.home;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandHome extends PlayerCommand {

	public CommandHome(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		if (!args.<String>getOne("name").isPresent()) { super.getCrucialCraft().getGame().getCommandManager().process(target, "homes"); return; }
		String name = args.<String>getOne("name").get();
		Optional<Teleport> home = super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).getHome(name);
		if (!home.isPresent()) { Messages.NO_SUCH_HOME.send(target); return; }
		Teleport tp = home.get();

		if (!tp.teleport(super.getCrucialCraft(), target, Text.of(TextColors.GOLD, "You were teleported to your home ", TextColors.RED, name, TextColors.GOLD, "."), false)) { Messages.NO_LAST_LOCATION.send(target); return; }
	}

	@Override
	public String getPermission() {
		return "cc.home.tp";
	}
}