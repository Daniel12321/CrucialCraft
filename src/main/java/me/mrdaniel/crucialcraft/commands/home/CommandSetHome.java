package me.mrdaniel.crucialcraft.commands.home;

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

public class CommandSetHome extends PlayerCommand {

	public CommandSetHome(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		String name = args.<String>getOne("name").get();
		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());

		if (file.getHome(name).isPresent() || file.getHomes().size() < super.getCrucialCraft().getConfig().getMaxHomes(target)) {
			file.setHome(name, new Teleport(target.getLocation(), target.getHeadRotation()));
			target.sendMessage(Text.of(TextColors.GOLD, "You set home ", TextColors.RED, name, TextColors.GOLD, " to your location."));
		}
		else { Messages.NO_MORE_HOMES.send(target); }
	}

	@Override
	public String getPermission() {
		return "cc.home.set";
	}
}