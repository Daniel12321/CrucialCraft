package me.mrdaniel.crucialcraft.commands.mail;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandMailClear extends PlayerCommand {

	public CommandMailClear(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).clearMail();
		target.sendMessage(Text.of(TextColors.GOLD, "You cleared your mail."));
	}

	@Override
	public String getPermission() {
		return "cc.mail.clear";
	}

	@Override
	public String getName() {
		return "Mail Clear";
	}
}