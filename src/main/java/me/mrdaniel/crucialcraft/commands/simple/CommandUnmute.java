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
import me.mrdaniel.crucialcraft.data.CCPlayerData;

public class CommandUnmute extends TargetPlayerCommand {

	public CommandUnmute(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		CCPlayerData data = target.get(CCPlayerData.class).get();
		data.setMuted(false);
		target.offer(data);

		target.sendMessage(Text.of(TextColors.GOLD, "You are no longer muted."));
	}

	@Override
	public String getPermission() {
		return "cc.unmute";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}