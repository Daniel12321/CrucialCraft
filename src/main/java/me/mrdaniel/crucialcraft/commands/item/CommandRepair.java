package me.mrdaniel.crucialcraft.commands.item;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.property.item.UseLimitProperty;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandRepair extends PlayerCommand {

	public CommandRepair(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		Optional<ItemStack> itemOpt = target.getItemInHand(HandTypes.MAIN_HAND);
		if (!itemOpt.isPresent()) { Messages.NO_ITEM_IN_HAND.send(target); return; }
		ItemStack item = itemOpt.get();

		Optional<UseLimitProperty> uselimit = item.getProperty(UseLimitProperty.class);
		if (!item.supports(Keys.ITEM_DURABILITY) || !uselimit.isPresent()) { Messages.ITEM_DOESNT_SUPPORT_DURABILITY.send(target); return; }
		int dura = uselimit.get().getValue();

		item.offer(Keys.ITEM_DURABILITY, dura);
		target.setItemInHand(HandTypes.MAIN_HAND, item);
		target.sendMessage(Text.of(TextColors.GOLD, "The item in your hand was fully repaired."));
	}

	@Override
	public String getPermission() {
		return "cc.repair";
	}
}