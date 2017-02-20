package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetOtherPlayerCommand;

public class CommandGive extends TargetOtherPlayerCommand {

	public CommandGive(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.player(cc, "target"), Argument.itemtype(cc, "type"), Argument.optional(Argument.integer("amount")));
	}

	@Override
	public void execute(final CommandSource src, final Player target, final Arguments args) {
		ItemType type = args.get("type");
		int amount = args.has("amount") ? args.get("amount") : 1;

		target.getInventory().offer(ItemStack.builder().itemType(type).quantity(amount).build());
		target.sendMessage(Text.of(TextColors.GOLD, "You received ", TextColors.RED, amount, " ", type.getName(), TextColors.GOLD, "."));
		src.sendMessage(Text.of(TextColors.GOLD, "You gave ", TextColors.RED, target.getName(), " ", amount, " ", type.getName(), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.give";
	}

	@Override
	public String getName() {
		return "Give";
	}
}