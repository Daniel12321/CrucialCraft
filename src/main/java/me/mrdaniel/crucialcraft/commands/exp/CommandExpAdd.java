package me.mrdaniel.crucialcraft.commands.exp;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;

public class CommandExpAdd extends TargetPlayerCommand {

	public CommandExpAdd(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		int value = args.<Integer>getOne("exp").get() + target.get(Keys.TOTAL_EXPERIENCE).orElse(0);
		target.offer(Keys.TOTAL_EXPERIENCE, value);
		target.sendMessage(Text.of(TextColors.GOLD, "Your exp was set to ", TextColors.RED, value, TextColors.GOLD, "."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You set ", TextColors.RED, target.getName(), TextColors.GOLD, "'s exp to ", TextColors.RED, value, TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.exp.add";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}