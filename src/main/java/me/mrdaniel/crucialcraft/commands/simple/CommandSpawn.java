package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandSpawn extends PlayerCommand {

	public CommandSpawn(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<Teleport> sp = super.getCrucialCraft().getDataFile().getSpawn();
		if (!sp.isPresent()) { Messages.SPAWN_NOT_SET.send(target).send(src); return; }
		Teleport spawn = sp.get();

		if (spawn.teleport(super.getCrucialCraft(), target)) {
			target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to spawn."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to spawn.")));
		}
		else {
			Messages.TELEPORT_DOESNT_EXIST.send(target).send(src);
		}
	}

	@Override
	public String getPermission() {
		return "cc.spawn";
	}
}