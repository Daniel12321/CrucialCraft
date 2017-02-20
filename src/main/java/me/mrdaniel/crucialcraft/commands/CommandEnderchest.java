package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerTargetSelfOrOtherPlayerCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandEnderchest extends PlayerTargetSelfOrOtherPlayerCommand {

	public CommandEnderchest(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<Player> src, final Player target, final Arguments args) {
		src.orElse(target).openInventory(target.getEnderChestInventory(), ServerUtils.getCause(super.getCrucialCraft().getContainer()));
	}

	@Override
	public String getPermission() {
		return "cc.enderchest";
	}

	@Override
	public String getName() {
		return "Enderchest";
	}
}