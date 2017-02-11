package me.mrdaniel.crucialcraft.commands.item;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;

public class CommandGive extends TargetPlayerCommand {

	public CommandGive(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		ItemType type = args.<ItemType>getOne("type").get();
		int amount = args.<Integer>getOne("amount").orElse(1);

		target.getInventory().offer(ItemStack.builder().itemType(type).quantity(amount).build());
		target.sendMessage(Text.of(TextColors.GOLD, "You received ", TextColors.RED, amount, " ", type.getName(), TextColors.GOLD, "."));
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You gave ", TextColors.RED, target.getName(), " ", amount, " ", type.getName(), TextColors.GOLD, ".")));
	}

	@Override
	public String getPermission() {
		return "cc.give";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}