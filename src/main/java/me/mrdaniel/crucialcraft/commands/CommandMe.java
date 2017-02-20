package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandMe extends SimpleCommand {

	public CommandMe(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.remaining("message"));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		if (src instanceof Player && super.getCrucialCraft().getPlayerData().get(((Player)src).getUniqueId()).isMuted()) { throw new CommandException("You cant talk while muted."); }

		Text name = (src instanceof Player) ? super.getCrucialCraft().getPlayerData().get(((Player)src).getUniqueId()).getNick().orElse(Text.of(((Player)src).getName())) : Text.of("Console") ;
		ServerUtils.broadcast(super.getCrucialCraft().getGame().getServer(), Text.of(TextColors.DARK_PURPLE, "* ", name, " ", TextColors.DARK_PURPLE, args.<String>get("message")));
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.me";
	}

	@Override
	public String getName() {
		return "Me";
	}
}