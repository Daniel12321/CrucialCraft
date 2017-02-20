package me.mrdaniel.crucialcraft.commands.homes;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.PermissionUtils;

public class CommandSetHome extends PlayerCommand {

	public CommandSetHome(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.string("name"));
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		String name = args.get("name");
		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());

		if (file.getHome(name).isPresent() || file.getHomes().size() < PermissionUtils.getMaxHomes(target)) {
			file.setHome(name, new Teleport(target.getLocation(), target.getHeadRotation()));
			target.sendMessage(Text.of(TextColors.GOLD, "You set home ", TextColors.RED, name, TextColors.GOLD, " to your location."));
		}
		else { throw new CommandException("You can not set any more homes."); }
	}

	@Override
	public String getName() {
		return "Set Home";
	}

	@Override
	public String getPermission() {
		return "cc.home.set";
	}
}