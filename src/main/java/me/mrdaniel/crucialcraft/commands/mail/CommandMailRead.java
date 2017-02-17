package me.mrdaniel.crucialcraft.commands.mail;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;

public class CommandMailRead extends PlayerCommand {

	public CommandMailRead(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		
	}

	@Override
	public String getPermission() {
		return "cc.mail.read";
	}
}