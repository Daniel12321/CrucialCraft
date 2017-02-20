package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetOtherPlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandTP extends TargetOtherPlayerCommand {

	public CommandTP(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.player(cc, "target"), Argument.optional(Argument.player(cc, "other")));
	}

	@Override
	public void execute(final CommandSource src, final Player target, final Arguments args) throws CommandException {
		if (args.has("other")) {
			Player other = args.get("other");
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			file.setLastLocation(new Teleport(target.getLocation(), target.getHeadRotation()));

			target.setLocationAndRotation(other.getLocation(), other.getHeadRotation());
			target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, other.getName(), TextColors.GOLD, "'s location."));
		}
		else {
			if (!(src instanceof Player)) { throw new CommandException("This command is for players only."); }
			Player source = (Player) src;

			PlayerFile file = super.getCrucialCraft().getPlayerData().get(source.getUniqueId());
			file.setLastLocation(new Teleport(source.getLocation(), source.getHeadRotation()));

			source.setLocationAndRotation(target.getLocation(), target.getHeadRotation());
			source.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, target.getName(), TextColors.GOLD, "'s location."));
		}
	}

	@Override
	public String getPermission() {
		return "cc.tp";
	}

	@Override
	public String getName() {
		return "TP";
	}
}