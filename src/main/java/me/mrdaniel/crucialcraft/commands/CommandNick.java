package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandNick extends TargetSelfOrOtherPlayerCommand{

	public CommandNick(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")), Argument.string("nick"));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) throws CommandException {
		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		String nick = args.get("nick");

		if (nick.equalsIgnoreCase("clear") || nick.equalsIgnoreCase("reset") || nick.equalsIgnoreCase("none") || nick.equalsIgnoreCase("remove")) {
			file.setNick(null);
			target.sendMessage(Text.of(TextColors.GOLD, "Your nickname was reset."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You reset ", TextColors.RED, target.getName(), TextColors.GOLD, "'s nickname.")));
		}
		else {
			if (nick.contains("&") && !src.orElse(target).hasPermission("cc.colors.nick")) { throw new CommandException("You dont have permission to use colors."); }
			file.setNick(TextUtils.toText(nick));
			target.sendMessage(Text.of(TextColors.GOLD, "Your nickname was set to ", TextUtils.toText(nick)));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s nickname to", TextColors.RED, TextUtils.toText(nick), TextColors.GOLD, ".")));
		}
	}

	@Override
	public String getPermission() {
		return "cc.nick";
	}

	@Override
	public String getName() {
		return "Nick";
	}
}