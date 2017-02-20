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
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandKick extends TargetOtherPlayerCommand {

	public CommandKick(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.player(cc, "target"), Argument.optional(Argument.remaining("reason")));
	}

	@Override
	public void execute(final CommandSource src, final Player target, final Arguments args) {
		String reason = args.has("reason") ? args.get("reason") : "&cNot Specified.";

		target.kick(TextUtils.toText(reason));
		src.sendMessage(Text.of(TextColors.GOLD, "You kicked ", TextColors.RED, target.getName(), TextColors.GOLD, " from the server."));
	}

	@Override
	public String getPermission() {
		return "cc.kick";
	}

	@Override
	public String getName() {
		return "Kick";
	}
}