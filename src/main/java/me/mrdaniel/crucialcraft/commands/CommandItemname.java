package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandItemname extends PlayerCommand {

	public CommandItemname(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.string("name"));
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		ItemStack hand = target.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException("You are not holding an item."));
		String name = args.get("name");
		if (name.contains("&") && !target.hasPermission("cc.colors.item.name")) { throw new CommandException("You dont have permission to use colors."); }

		hand.offer(Keys.DISPLAY_NAME, TextUtils.toText(name));
		target.setItemInHand(HandTypes.MAIN_HAND, hand);
		target.sendMessage(Text.of(TextColors.GOLD, "The item in your hand was renamed to ", TextColors.RED, TextUtils.toText(name), TextColors.GOLD, "."));
	}

	@Override
	public String getName() {
		return "Itemname";
	}

	@Override
	public String getPermission() {
		return "cc.itemname";
	}
}