package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandHelpop extends SimpleCommand {

	public CommandHelpop(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.remaining("message"));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		String message = args.<String>get("message");
		String name = src instanceof Player ? ((Player)src).getName() : "Console";
		Text msg = TextUtils.toText("&6[&2HelpOp&6] " + name + ": " + message);
		super.getCrucialCraft().getGame().getServer().getOnlinePlayers().forEach(p -> {
			if (p.hasPermission("cc.helpop.receive")) { p.sendMessage(msg); }
		});
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.helpop.send";
	}

	@Override
	public String getName() {
		return "HelpOp";
	}
}