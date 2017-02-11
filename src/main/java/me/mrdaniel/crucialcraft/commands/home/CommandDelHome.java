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

public class CommandDelHome extends PlayerCommand {

	public CommandDelHome(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		String name = args.<String>getOne("name").get();

		CCPlayerData data = target.get(CCPlayerData.class).get();
		Optional<Teleport> home = data.getHome(name);
		if (!home.isPresent()) { Messages.NO_SUCH_HOME.send(target); return; }

		data.setHome(name, null);
		target.offer(data);
		target.sendMessage(Text.of(TextColors.GOLD, "You deleted home ", TextColors.RED, name, TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.home.del";
	}
}