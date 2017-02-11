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
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandUnjail extends TargetPlayerCommand {

	public CommandUnjail(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		CCPlayerData data = target.get(CCPlayerData.class).get();
		if (!data.getJailed()) { Messages.IS_NOT_JAILED.send(src.orElse(target)); return; }

		data.setJailed(false);
		target.offer(data);

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