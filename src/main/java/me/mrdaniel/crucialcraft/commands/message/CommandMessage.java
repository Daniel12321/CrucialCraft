package me.mrdaniel.crucialcraft.commands.message;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandMessage extends TargetPlayerCommand {

	public CommandMessage(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Text targetname = TextUtils.toText(target.get(CCPlayerData.class).get().getNick().orElse(target.getName()));
		Text srcname = (src.get() instanceof Player) ? TextUtils.toText(((Player)src.get()).get(CCPlayerData.class).get().getNick().orElse(((Player)src.get()).getName())) : Text.of("Console");

		String msg = args.<String>getOne("message").get();
		Text txt = src.get().hasPermission("cc.colors.message") ? TextUtils.toText(msg) : Text.of(msg);

		target.sendMessage(Text.of(TextColors.DARK_GRAY, srcname, TextColors.DARK_GRAY, " -> You: ", txt));
		src.get().sendMessage(Text.of(TextColors.DARK_GRAY, "You -> ", targetname, TextColors.DARK_GRAY, ": ", txt));
	}

	@Override
	public String getPermission() {
		return "cc.msg";
	}

	@Override
	public boolean canTargetSelf() {
		return false;
	}
}