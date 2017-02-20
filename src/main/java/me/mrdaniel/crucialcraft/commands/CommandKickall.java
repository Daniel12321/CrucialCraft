package me.mrdaniel.crucialcraft.commands;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandKickall extends SimpleCommand {

	public CommandKickall(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) {
		String reason = args.has("reason") ? args.get("reason") : "&cNot Specified.";

		List<Player> players = Lists.newArrayList(super.getCrucialCraft().getGame().getServer().getOnlinePlayers());
		if (src instanceof Player) { players.remove((Player) src); }

		players.forEach(p -> p.kick(TextUtils.toText(reason)));
		src.sendMessage(Text.of(TextColors.GOLD, "You kicked everyone from the server."));
	}

	@Override
	public String getPermission(@Nonnull final Arguments args) {
		return "cc.kickall";
	}

	@Override
	public String getName() {
		return "KickAll";
	}
}