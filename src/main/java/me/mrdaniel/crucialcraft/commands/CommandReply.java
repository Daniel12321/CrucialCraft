package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandReply extends PlayerCommand {

	public CommandReply(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		String last_messager = super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getLastMessager().orElseThrow(() -> new CommandException("Last messager was not found."));
		Player p = super.getCrucialCraft().getGame().getServer().getPlayer(last_messager).orElseThrow(() -> new CommandException("Last messager was not found."));

		super.getCrucialCraft().getGame().getCommandManager().process(src, "/msg " + p.getName() + " " + args.<String>get("message"));
	}

	@Override
	public String getPermission() {
		return "cc.reply";
	}

	@Override
	public String getName() {
		return "Reply";
	}
}