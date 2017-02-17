package me.mrdaniel.crucialcraft.commands.simple;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandEnchant extends PlayerCommand {

	public CommandEnchant(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		Optional<ItemStack> item = target.getItemInHand(HandTypes.MAIN_HAND);
		if (!item.isPresent()) { Messages.NO_ITEM_IN_HAND.send(target); return; }
		ItemStack hand = item.get();

		Enchantment type = args.<Enchantment>getOne("type").get();
		int level = args.<Integer>getOne("level").get();

		if ((!hand.supports(Keys.ITEM_ENCHANTMENTS) || !type.canBeAppliedToStack(hand)) && !target.hasPermission("cc.enchant.unsafe")) { Messages.NO_UNSAFE_ENCHANT_PERMISSION.send(target); return; }
		if (level > type.getMaximumLevel() && !target.hasPermission("cc.enchant.unsafe")) { Messages.NO_UNSAFE_ENCHANT_PERMISSION.send(target); return; }

		ItemEnchantment enchantment = new ItemEnchantment(type, level);
		List<ItemEnchantment> ench = hand.get(Keys.ITEM_ENCHANTMENTS).orElse(Lists.newArrayList());
		ench.removeIf(e -> e.getEnchantment() == type);
		ench.add(enchantment);
		hand.offer(Keys.ITEM_ENCHANTMENTS, ench);

		target.setItemInHand(HandTypes.MAIN_HAND, hand);
		target.sendMessage(Text.of(TextColors.GOLD, "You added ", TextColors.RED, type.getName(), " ", level, " to the item in your hand."));
	}

	@Override
	public String getPermission() {
		return "cc.enchant.safe";
	}
}