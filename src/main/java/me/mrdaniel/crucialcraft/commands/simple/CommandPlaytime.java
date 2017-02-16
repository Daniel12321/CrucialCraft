package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandPlaytime extends PermissionCommand {

	public CommandPlaytime(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		Optional<User> u = args.<User>getOne("target");

		if (u.isPresent()) {
			Optional<Player> p = u.get().getPlayer();

			if (p.isPresent()) {
				PlayerFile file = super.getCrucialCraft().getPlayerData().get(p.get().getUniqueId());
				src.sendMessage(Text.of(TextColors.RED, p.get().getName(), TextColors.GOLD, "'s total playtime is ", TextColors.RED, TextUtils.getTimeFormat(file.getCurrentPlaytime()), TextColors.GOLD, "."));
			}
			else {
				Optional<PlayerFile> data = super.getCrucialCraft().getPlayerData().getOffline(u.get().getUniqueId());
				if (data.isPresent()) { src.sendMessage(Text.of(TextColors.RED, u.get().getName(), TextColors.GOLD, "'s total playtime is ", TextColors.RED, TextUtils.getTimeFormat(data.get().getCurrentPlaytime()), TextColors.GOLD, ".")); }
				else { Messages.NO_SUCH_USER.send(src); }
			}
		}
		else if (src instanceof Player) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(((Player)src).getUniqueId());
			src.sendMessage(Text.of(TextColors.GOLD, "Your total playtime is ", TextColors.RED, TextUtils.getTimeFormat(file.getCurrentPlaytime()), TextColors.GOLD, "."));
		}
		else { Messages.NOT_PLAYER.send(src); }
	}

	@Override
	public String getPermission() {
		return "cc.playtime";
	}
}