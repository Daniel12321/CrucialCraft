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
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandMessage extends TargetOtherPlayerCommand {

	public CommandMessage(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.player(cc, "target"), Argument.remaining("message"));
	}

	@Override
	public void execute(final CommandSource src, final Player target, final Arguments args) throws CommandException {
		if (src instanceof Player && super.getCrucialCraft().getPlayerData().get(((Player)src).getUniqueId()).isMuted()) { throw new CommandException("You cant talk while muted."); }

		Text targetname = super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).getNick().orElse(Text.of(target.getName()));
		Text srcname = (src instanceof Player) ? super.getCrucialCraft().getPlayerData().get(((Player)src).getUniqueId()).getNick().orElse(Text.of(((Player)src).getName())) : Text.of("Console");

		String msg = args.get("message");
		Text txt = src.hasPermission("cc.colors.message") ? TextUtils.toText(msg) : Text.of(msg);

		target.sendMessage(Text.of(TextColors.DARK_GRAY, srcname, TextColors.DARK_GRAY, " -> You: ", txt));
		src.sendMessage(Text.of(TextColors.DARK_GRAY, "You -> ", targetname, TextColors.DARK_GRAY, ": ", txt));
	}

	@Override
	public String getPermission() {
		return "cc.msg";
	}

	@Override
	public String getName() {
		return "Message";
	}
}