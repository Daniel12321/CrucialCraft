package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandDispose extends PlayerCommand {

	public CommandDispose(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) {
		Inventory inv = Inventory.builder()
				.of(InventoryArchetypes.DOUBLE_CHEST)
				.property(InventoryDimension.PROPERTY_NAME, InventoryDimension.of(9, 6))
				.property(InventoryTitle.PROPERTY_NAME, InventoryTitle.of(Text.of("Trash Can")))
				.build(super.getCrucialCraft());

		src.openInventory(inv, ServerUtils.getCause(super.getCrucialCraft().getContainer()));
	}

	@Override
	public String getPermission() {
		return "cc.dispose";
	}

	@Override
	public String getName() {
		return "Dispose";
	}
}