package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandWorkbench extends PlayerCommand {

	public CommandWorkbench(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		target.openInventory(Inventory.builder().of(InventoryArchetypes.WORKBENCH).build(super.getCrucialCraft()), ServerUtils.getCause(super.getCrucialCraft().getContainer()));
	}

	@Override
	public String getPermission() {
		return "cc.workbench";
	}
}