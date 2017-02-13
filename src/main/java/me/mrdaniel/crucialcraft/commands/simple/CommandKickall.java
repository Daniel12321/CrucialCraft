package me.mrdaniel.crucialcraft.commands.simple;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandKickall extends PermissionCommand {

	public CommandKickall(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		String reason = args.<String>getOne("reason").orElse("&cNot Specified.");

		List<Player> players = Lists.newArrayList(super.getCrucialCraft().getGame().getServer().getOnlinePlayers());
		if (src instanceof Player) { players.remove((Player) src); }

		players.forEach(p -> p.kick(TextUtils.toText(reason)));
		src.sendMessage(Text.of(TextColors.GOLD, "You kicked everyone from the server."));
	}

	@Override
	public String getPermission() {
		return "cc.kickall";
	}
}