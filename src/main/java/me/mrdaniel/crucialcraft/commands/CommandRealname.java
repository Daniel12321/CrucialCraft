package me.mrdaniel.crucialcraft.commands;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandRealname extends SimpleCommand {

	public CommandRealname(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.string("nick"));
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		String name = args.get("nick");
		Player player = this.getNickPlayer(name).orElseThrow(() -> new CommandException("No player with that nick exists."));

		src.sendMessage(Text.of(super.getCrucialCraft().getPlayerData().get(player.getUniqueId()).getNick().get(), TextColors.GOLD, "'s real name is ", TextColors.RED, player.getName(), TextColors.GOLD, "."));
	}

	private Optional<Player> getNickPlayer(@Nonnull final String nick) {
		Collection<Player> players = super.getCrucialCraft().getGame().getServer().getOnlinePlayers();
		for (Player p : players) {
			Optional<Text> n = super.getCrucialCraft().getPlayerData().get(p.getUniqueId()).getNick();
			if (n.isPresent() && n.get().toPlain().equalsIgnoreCase(nick)) { return Optional.of(p); }
		}
		for (Player p : players) {
			Optional<Text> n = super.getCrucialCraft().getPlayerData().get(p.getUniqueId()).getNick();
			if (n.isPresent() && n.get().toPlain().contains(nick)) { return Optional.of(p); }
		}
		return Optional.empty();
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.realname";
	}

	@Override
	public String getName() {
		return "Realname";
	}
}