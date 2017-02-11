package me.mrdaniel.crucialcraft.commands.home;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandHome extends PlayerCommand {

	public CommandHome(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		if (!args.<String>getOne("name").isPresent()) { super.getCrucialCraft().getGame().getCommandManager().process(target, "homes"); return; }
		String name = args.<String>getOne("name").get();
		Optional<Teleport> home = target.get(CCPlayerData.class).get().getHome(name);
		if (!home.isPresent()) { Messages.NO_SUCH_HOME.send(src.orElse(target)); return; }
		Teleport tp = home.get();

		if (src.isPresent()) {
			if (!(src.get() instanceof Player)) { Messages.NOT_PLAYER.send(src.get()); return; }
			Player source = (Player) src.get();

			tp.teleport(super.getCrucialCraft(), source);
			source.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, target.getName(), TextColors.GOLD, "'s home ", TextColors.RED, name, TextColors.GOLD, "."));
		}
		else {
			tp.teleport(super.getCrucialCraft(), target);
			target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to your home ", TextColors.RED, name, TextColors.GOLD, "."));
		}
	}

	@Override
	public String getPermission() {
		return "cc.home.tp";
	}
}