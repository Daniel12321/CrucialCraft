package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandUnbreakable extends PlayerCommand {

	public CommandUnbreakable(@Nonnull final CrucialCraft cc) {
		super(cc);
	}


	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		ItemStack hand = target.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException("You are not holding an item."));
		if (!hand.supports(Keys.UNBREAKABLE)) { throw new CommandException("The item in your hand doesnt support unbreakable."); }

		boolean value = !hand.get(Keys.UNBREAKABLE).orElse(false);
		hand.offer(Keys.UNBREAKABLE, value);
		target.setItemInHand(HandTypes.MAIN_HAND, hand);

		if (value) { target.sendMessage(Text.of(TextColors.GOLD, "The item in your hand is now unbreakable.")); }
		else { target.sendMessage(Text.of(TextColors.GOLD, "The item in your hand is no longer unbreakable.")); }
	}

	@Override
	public String getPermission() {
		return "cc.unbreakable";
	}

	@Override
	public String getName() {
		return "cc.unbreakable";
	}
}