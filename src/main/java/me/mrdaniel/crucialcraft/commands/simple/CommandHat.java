package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandHat extends PlayerCommand {

	public CommandHat(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<ItemStack> handOpt = target.getItemInHand(HandTypes.MAIN_HAND);
		if (!handOpt.isPresent()) { Messages.NO_ITEM_IN_HAND.send(src.orElse(target)); }

		target.setItemInHand(HandTypes.MAIN_HAND, target.getHelmet().orElse(null));
		target.setHelmet(handOpt.get());
	}

	@Override
	public String getPermission() {
		return "cc.hat";
	}
}