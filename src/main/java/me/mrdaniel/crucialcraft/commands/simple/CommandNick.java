package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandNick extends TargetPlayerCommand{

	public CommandNick(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<String> nick = args.<String>getOne("nick");

		if (nick.isPresent()) {
			if (nick.get().contains("&") && !src.orElse(target).hasPermission("cc.colors.nick")) { Messages.NO_COLOR_PERMISSION.send(src.orElse(target)); return; }
			target.sendMessage(Text.of(TextColors.GOLD, "Your nickname was set to ", TextUtils.toText(nick.get())));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s nickname to", TextColors.RED, TextUtils.toText(nick.get()), TextColors.GOLD, ".")));
		}
		else {
			target.sendMessage(Text.of(TextColors.GOLD, "Your nickname was reset."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You reset ", TextColors.RED, target.getName(), TextColors.GOLD, "'s nickname.")));
		}

		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		file.setNick(nick.orElse(null));
	}

	@Override
	public String getPermission() {
		return "cc.nick";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}