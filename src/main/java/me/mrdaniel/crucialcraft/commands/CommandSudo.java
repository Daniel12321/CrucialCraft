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

public class CommandSudo extends TargetOtherPlayerCommand {

	public CommandSudo(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.player(cc, "target"), Argument.remaining("command"));
	}

	@Override
	public void execute(final CommandSource src, final Player target, final Arguments args) {
		String cmd = args.get("command");
		super.getCrucialCraft().getGame().getCommandManager().process(target, cmd);
		src.sendMessage(Text.of(TextColors.GOLD, "You made ", TextColors.RED, target.getName(), TextColors.GOLD, " perform ", TextColors.RED, cmd, TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.sudo";
	}

	@Override
	public String getName() {
		return "Sudo";
	}
}