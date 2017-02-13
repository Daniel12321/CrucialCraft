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
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandBack extends TargetPlayerCommand {

	public CommandBack(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		CCPlayerData data = target.get(CCPlayerData.class).get();
		if (data.getLastLocation().isPresent()) {
			Teleport tp = data.getLastLocation().get();

			data.setLastLocation(new Teleport(target.getLocation(), target.getHeadRotation()));
			target.offer(data);

			if (tp.teleport(super.getCrucialCraft(), target)) {
				target.sendMessage(Text.of(TextColors.GOLD, "You were teleported back to your last location."));
				src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to his last location.")));
			}
			else { Messages.TELEPORT_DOESNT_EXIST.send(src.orElse(target)); }
		}
		else { Messages.NO_LAST_LOCATION.send(src.orElse(target)); }
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