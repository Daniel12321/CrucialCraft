package me.mrdaniel.crucialcraft.commands.home;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerTargetPlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandHome extends PlayerTargetPlayerCommand {

	public CommandHome(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Player source, final CommandContext args) {
		if (!args.<String>getOne("name").isPresent()) { super.getCrucialCraft().getGame().getCommandManager().process(target, "homes"); return; }
		String name = args.<String>getOne("name").get();
		Optional<Teleport> home = target.get(CCPlayerData.class).get().getHome(name);
		if (!home.isPresent()) { Messages.NO_SUCH_HOME.send(source); return; }
		Teleport tp = home.get();

		if (!tp.teleport(super.getCrucialCraft(), source)) { Messages.NO_LAST_LOCATION.send(source); return; }

		if (source.equals(target)) { source.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, target.getName(), TextColors.GOLD, "'s home ", TextColors.RED, name, TextColors.GOLD, ".")); }
		else { source.sendMessage(Text.of(TextColors.GOLD, "You were teleported to your home ", TextColors.RED, name, TextColors.GOLD, ".")); }
	}

	@Override
	public String getPermission() {
		return "cc.home.tp";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}