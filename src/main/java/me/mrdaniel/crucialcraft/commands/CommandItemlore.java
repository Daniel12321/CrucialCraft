package me.mrdaniel.crucialcraft.commands;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandItemlore extends PlayerCommand {

	public CommandItemlore(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.string("text"), Argument.optional(Argument.integer("line")));
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		ItemStack hand = target.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException("You are not holding an item."));
		List<Text> lore = hand.get(Keys.ITEM_LORE).orElse(Lists.newArrayList());

		String text = args.get("text");
		if (text.contains("&") && !target.hasPermission("cc.colors.item.lore")) { throw new CommandException("You dont have permission to use colors."); }

		if (args.has("line")) { lore.set(args.get("line"), TextUtils.toText(text)); }
		else { lore.add(TextUtils.toText(text)); }

		hand.offer(Keys.ITEM_LORE, lore);
		target.setItemInHand(HandTypes.MAIN_HAND, hand);
		target.sendMessage(Text.of(TextColors.GOLD, "You added a line of lore to the item in your hand."));
	}

	@Override
	public String getName() {
		return "Itemlore";
	}

	@Override
	public String getPermission() {
		return "cc.itemlore";
	}
}