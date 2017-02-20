package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandItem extends PlayerCommand {

	public CommandItem(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.itemtype(cc, "type"), Argument.optional(Argument.integer("amount")));
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		ItemType type = args.get("type");
		int amount = args.has("amount") ? args.get("amount") : 1;

		target.getInventory().offer(ItemStack.builder().itemType(type).quantity(amount).build());
		target.sendMessage(Text.of(TextColors.GOLD, "You received ", TextColors.RED, amount, " ", type.getName(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.item";
	}

	@Override
	public String getName() {
		return "Item";
	}
}