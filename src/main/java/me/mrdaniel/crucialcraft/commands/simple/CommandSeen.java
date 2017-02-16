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
import me.mrdaniel.crucialcraft.commands.TargetUserCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandSeen extends TargetUserCommand {

	public CommandSeen(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final User target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<Player> p = target.getPlayer();

		if (p.isPresent()) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			src.orElse(p.get()).sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, " has been online for ", TextUtils.getTimeFormat(System.currentTimeMillis() - file.getLastLogin()), "."));
		}
		else {
			Optional<PlayerFile> data = super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId());
			if (!data.isPresent()) { Messages.NO_SUCH_USER.send(src); return; }

			src.orElse(p.get()).sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, " was last seen ", TextColors.RED, TextUtils.getTimeFormat(System.currentTimeMillis() - data.get().getLastLogout()), TextColors.GOLD, " ago."));
		}
//		String target = args.<String>getOne("target").get();
//
//		Optional<Player> pOpt = super.getCrucialCraft().getGame().getServer().getPlayer(target);
//		if (pOpt.isPresent()) {
//			Player p = pOpt.get();
//			PlayerFile file = super.getCrucialCraft().getPlayerData().get(p.getUniqueId());
//			src.sendMessage(Text.of(TextColors.RED, p.getName(), TextColors.GOLD, " has been online for ", TextUtils.getTimeFormat(System.currentTimeMillis() - file.getLastLogin()), "."));
//			return;
//		}
//
//		Optional<PlayerFile> data = super.getCrucialCraft().getPlayerData().getOffline(target);
//		if (!data.isPresent()) { Messages.NO_SUCH_USER.send(src); return; }
//
//		src.sendMessage(Text.of(TextColors.RED, target, TextColors.GOLD, " was last seen ", TextColors.RED, TextUtils.getTimeFormat(System.currentTimeMillis() - data.get().getLastLogout()), TextColors.GOLD, " ago."));
	}

	@Override
	public String getPermission() {
		return "cc.seen";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}