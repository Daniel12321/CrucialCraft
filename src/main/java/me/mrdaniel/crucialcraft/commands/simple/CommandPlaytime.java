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
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandPlaytime extends TargetPlayerCommand {

	public CommandPlaytime(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		CCPlayerData data = target.get(CCPlayerData.class).get();

		if (src.isPresent()) { src.get().sendMessage(Text.of(TextColors.RED, target.getName(), TextColors.GOLD, "'s total playtime: ", TextColors.RED, TextUtils.getTimeFormat(data.getCurrentPlaytime()), TextColors.GOLD, ".")); }
		else { target.sendMessage(Text.of(TextColors.GOLD, "Your total playtime: ", TextColors.RED, TextUtils.getTimeFormat(data.getCurrentPlaytime()), TextColors.GOLD, ".")); }
	}

	@Override
	public String getPermission() {
		return "cc.playtime";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}