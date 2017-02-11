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

public class CommandSudo extends TargetPlayerCommand {

	public CommandSudo(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		String cmd = args.<String>getOne("command").get();
		super.getCrucialCraft().getGame().getCommandManager().process(target, cmd);
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You made ", TextColors.RED, target.getName(), TextColors.GOLD, " perform ", TextColors.RED, cmd, TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.sudo";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}