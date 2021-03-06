package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandWorkbench extends PlayerCommand {

	public CommandWorkbench(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) {
		src.openInventory(Inventory.builder().of(InventoryArchetypes.WORKBENCH).build(super.getCrucialCraft()), ServerUtils.getCause(super.getCrucialCraft().getContainer()));
	}

	@Override
	public String getPermission() {
		return "cc.workbench";
	}

	@Override
	public String getName() {
		return "Workbench";
	}
}