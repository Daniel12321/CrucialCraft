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

public class CommandSetHome extends PlayerCommand {

	public CommandSetHome(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		String name = args.<String>getOne("name").get();
		CCPlayerData data = target.get(CCPlayerData.class).get();

		if (data.getHome(name).isPresent() || data.getHomes().size() < super.getCrucialCraft().getConfig().getMaxHomes(target)) {
			data.setHome(name, new Teleport(target.getLocation(), target.getHeadRotation()));
			target.offer(data);
			target.sendMessage(Text.of(TextColors.GOLD, "You set home ", TextColors.RED, name, TextColors.GOLD, " to your location."));
		}
		else { Messages.NO_MORE_HOMES.send(target); }
	}

	@Override
	public String getPermission() {
		return "cc.home.set";
	}
}