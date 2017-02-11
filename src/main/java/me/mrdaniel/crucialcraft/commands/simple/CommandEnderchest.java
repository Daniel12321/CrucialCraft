package me.mrdaniel.crucialcraft.commands.simple;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerTargetPlayerCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandEnderchest extends PlayerTargetPlayerCommand {

	public CommandEnderchest(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Player src, final CommandContext args) {
		src.openInventory(target.getEnderChestInventory(), ServerUtils.getGenericCause(super.getCrucialCraft().getContainer()));
	}

	@Override
	public String getPermission() {
		return "cc.enderchest";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}