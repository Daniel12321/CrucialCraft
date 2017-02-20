package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandMore extends PlayerCommand {

	public CommandMore(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		ItemStack hand = src.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException("You are not holding an item."));

		hand.setQuantity(hand.getMaxStackQuantity());
		src.setItemInHand(HandTypes.MAIN_HAND, hand);
		src.sendMessage(Text.of(TextColors.GOLD, "Your stack was filled."));
	}

	@Override
	public String getPermission() {
		return "cc.more";
	}

	@Override
	public String getName() {
		return "More";
	}
}