package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandRealname extends PermissionCommand {

	public CommandRealname(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		String name = args.<String>getOne("nick").get();

		Player player = this.getNickPlayer(name);
		if (player == null) { Messages.NO_SUCH_NICK.send(src); return; }

		src.sendMessage(Text.of(TextUtils.toText(super.getCrucialCraft().getPlayerData().get(player.getUniqueId()).getNick().get()), TextColors.GOLD, "'s real name is ", TextColors.RED, player.getName(), TextColors.GOLD, "."));
	}

	private Player getNickPlayer(@Nonnull final String nick) {
		Collection<Player> players = super.getCrucialCraft().getGame().getServer().getOnlinePlayers();
		for (Player p : players) {
			Optional<String> n = super.getCrucialCraft().getPlayerData().get(p.getUniqueId()).getNick();
			if (n.isPresent() && n.get().equalsIgnoreCase(nick)) { return p; }
		}
		for (Player p : players) {
			Optional<String> n = super.getCrucialCraft().getPlayerData().get(p.getUniqueId()).getNick();
			if (n.isPresent() && n.get().contains(nick)) { return p; }
		}
		return null;
	}

	@Override
	public String getPermission() {
		return "cc.realname";
	}
}