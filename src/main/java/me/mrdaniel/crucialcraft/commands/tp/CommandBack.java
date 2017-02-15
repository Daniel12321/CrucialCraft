package me.mrdaniel.crucialcraft.commands.tp;

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
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandBack extends TargetPlayerCommand {

	public CommandBack(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		if (!file.getLastLocation().isPresent()) { Messages.NO_LAST_LOCATION.send(src.orElse(target)); return; }
		Teleport tp = file.getLastLocation().get();

		if (tp.teleport(super.getCrucialCraft(), target)) {
			target.sendMessage(Text.of(TextColors.GOLD, "You were teleported back to your last location."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to his last location.")));
		}
		else { Messages.TELEPORT_DOESNT_EXIST.send(src.orElse(target)); }
	}

	@Override
	public String getPermission() {
		return "cc.back";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}