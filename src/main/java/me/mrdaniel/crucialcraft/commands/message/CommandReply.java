package me.mrdaniel.crucialcraft.commands.message;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandReply extends PlayerCommand {

	public CommandReply(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		CCPlayerData data = target.get(CCPlayerData.class).get();
		if (data.getLastMessager().isPresent()) {
			String last_messager = data.getLastMessager().get();
			Optional<Player> p = super.getCrucialCraft().getGame().getServer().getPlayer(last_messager);
			if (!p.isPresent()) { Messages.NO_LAST_MESSAGER.send(target); return; }

			String msg = args.<String>getOne("message").get();
			super.getCrucialCraft().getGame().getCommandManager().process(target, "/msg " + last_messager + " " + msg);
		}
		else {
			Messages.NO_LAST_MESSAGER.send(target);
		}
	}

	@Override
	public String getPermission() {
		return "cc.reply";
	}
}