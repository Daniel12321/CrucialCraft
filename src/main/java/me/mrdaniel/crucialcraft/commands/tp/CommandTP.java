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

public class CommandTP extends TargetPlayerCommand {

	public CommandTP(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<Player> o = args.<Player>getOne("other");
		if (o.isPresent()) {
			Player other = o.get();

			CCPlayerData data = target.get(CCPlayerData.class).get();
			data.setLastLocation(new Teleport(target.getLocation(), target.getHeadRotation()));
			target.offer(data);

			target.setLocationAndRotation(other.getLocation(), other.getHeadRotation());
			target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, other.getName(), TextColors.GOLD, "'s location."));
		}
		else {
			if (!src.isPresent()) { Messages.NO_TARGET_YOURSELF.send(target); return; }
			if (!(src.get() instanceof Player)) { Messages.NOT_PLAYER.send(src.get()); return; }
			Player source = (Player) src.get();

			CCPlayerData data = source.get(CCPlayerData.class).get();
			data.setLastLocation(new Teleport(source.getLocation(), source.getHeadRotation()));
			source.offer(data);

			source.setLocationAndRotation(target.getLocation(), target.getHeadRotation());
			source.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, target.getName(), TextColors.GOLD, "'s location."));
		}
	}

	@Override
	public String getPermission() {
		return "cc.tp";
	}
	@Override
	public boolean canTargetSelf() {
		return false;
	}
}