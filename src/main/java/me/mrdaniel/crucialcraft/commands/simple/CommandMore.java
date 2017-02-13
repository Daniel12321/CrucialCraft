package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandMore extends TargetPlayerCommand {

	public CommandMore(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<ItemStack> itemOpt = target.getItemInHand(HandTypes.MAIN_HAND);
		if (!itemOpt.isPresent()) { Messages.NO_ITEM_IN_HAND.send(src.orElse(target)); return; }
		ItemStack item = itemOpt.get();

		item.setQuantity(item.getMaxStackQuantity());
		target.setItemInHand(HandTypes.MAIN_HAND, item);
		target.sendMessage(Text.of(TextColors.GOLD, "Your stack was filled."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You filled ", TextColors.RED, target.getName(), TextColors.GOLD, "'s stack.")));
	}

	@Override
	public String getPermission() {
		return "cc.more";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}