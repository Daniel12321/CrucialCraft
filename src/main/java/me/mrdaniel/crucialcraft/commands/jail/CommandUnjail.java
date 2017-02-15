package me.mrdaniel.crucialcraft.commands.jail;

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

public class CommandUnjail extends TargetPlayerCommand {

	public CommandUnjail(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
		if (!file.isJailed()) { Messages.IS_NOT_JAILED.send(src.orElse(target)); return; }

		file.setJailed(false);

		Optional<Teleport> spawn = super.getCrucialCraft().getDataFile().getSpawn();
		if (!(spawn.isPresent() && spawn.get().teleport(super.getCrucialCraft(), target))) { target.setLocation(target.getWorld().getSpawnLocation()); }

		target.sendMessage(Text.of(TextColors.GOLD, "You are no longer jailed."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You unjailed ", TextColors.RED, target.getName(), TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.jail.toggle";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}