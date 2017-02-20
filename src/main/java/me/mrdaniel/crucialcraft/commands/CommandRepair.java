package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.property.item.UseLimitProperty;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandRepair extends PlayerCommand {

	public CommandRepair(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		ItemStack hand = src.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException("You are not holding an item."));

		UseLimitProperty uselimit = hand.getProperty(UseLimitProperty.class).orElseThrow(() -> new CommandException("This item doesnt support durability."));
		if (!hand.supports(Keys.ITEM_DURABILITY)) { throw new CommandException("This item doesnt support durability."); }

		hand.offer(Keys.ITEM_DURABILITY, uselimit.getValue());
		src.setItemInHand(HandTypes.MAIN_HAND, hand);
		src.sendMessage(Text.of(TextColors.GOLD, "The item in your hand was fully repaired."));
	}

	@Override
	public String getPermission() {
		return "cc.repair";
	}

	@Override
	public String getName() {
		return "Repair";
	}
}