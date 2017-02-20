package me.mrdaniel.crucialcraft.commands;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;

public class CommandList extends SimpleCommand {

	public CommandList(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) {
		List<Player> players = Lists.newArrayList(super.getCrucialCraft().getGame().getServer().getOnlinePlayers());
		if (!src.hasPermission("cc.seevanish")) { players = players.stream().filter(p -> !p.get(Keys.INVISIBLE).orElse(false)).collect(Collectors.toList()); }

		src.sendMessage(Text.of(TextColors.GOLD, "There are ", TextColors.RED, players.size(), " / ", super.getCrucialCraft().getGame().getServer().getMaxPlayers(), TextColors.GOLD, " players online:"));
		if (players.isEmpty()) { return; }

		Text.Builder txt = Text.builder().append(Text.of(TextColors.RED, super.getCrucialCraft().getPlayerData().get(players.get(0).getUniqueId()).getNick().orElse(Text.of(players.get(0).getName()))));
		for (int i = 1; i < players.size(); i++) {
			txt.append(Text.of(TextColors.GOLD, ", ", TextColors.RED, super.getCrucialCraft().getPlayerData().get(players.get(i).getUniqueId()).getNick().orElse(Text.of(players.get(i).getName()))));
		}
		src.sendMessage(txt.build());
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.list";
	}

	@Override
	public String getName() {
		return "List";
	}
}