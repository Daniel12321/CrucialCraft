package me.mrdaniel.crucialcraft.commands.simple;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandList extends PermissionCommand {

	public CommandList(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		List<Player> players = Lists.newArrayList(super.getCrucialCraft().getGame().getServer().getOnlinePlayers());
		if (!src.hasPermission("cc.seevanish")) { players = players.stream().filter(p -> !p.get(Keys.INVISIBLE).orElse(false)).collect(Collectors.toList()); }

		src.sendMessage(Text.of(TextColors.GOLD, "There are ", TextColors.RED, players.size(), " / ", super.getCrucialCraft().getGame().getServer().getMaxPlayers(), TextColors.GOLD, " players online:"));

		Text.Builder txt = Text.builder().append(Text.of(TextColors.RED, TextUtils.toText(super.getCrucialCraft().getPlayerData().get(players.get(0).getUniqueId()).getNick().orElse(players.get(0).getName()))));
		for (int i = 1; i < players.size(); i++) {
			txt.append(Text.of(TextColors.GOLD, ", ", TextColors.RED, TextUtils.toText(super.getCrucialCraft().getPlayerData().get(players.get(i).getUniqueId()).getNick().orElse(players.get(i).getName()))));
		}
		src.sendMessage(txt.build());
	}

	@Override
	public String getPermission() {
		return "cc.list";
	}
}