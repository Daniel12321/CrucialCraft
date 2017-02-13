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
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandKick extends TargetPlayerCommand {

	public CommandKick(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		String reason = args.<String>getOne("reason").orElse("&cNot Specified.");

		target.kick(TextUtils.toText(reason));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You kicked ", TextColors.RED, target.getName(), TextColors.GOLD, " from the server.")));
	}

	@Override
	public String getPermission() {
		return "cc.kick";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}