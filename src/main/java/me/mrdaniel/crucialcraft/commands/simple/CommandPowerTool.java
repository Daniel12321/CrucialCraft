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
import me.mrdaniel.crucialcraft.data.PowerToolData;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandPowerTool extends TargetPlayerCommand {

	public CommandPowerTool(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		String command = args.<String>getOne("command").get();

		if (!target.getItemInHand(HandTypes.MAIN_HAND).isPresent()) { Messages.NO_ITEM_IN_HAND.send(target); }

		ItemStack hand = target.getItemInHand(HandTypes.MAIN_HAND).get();
		hand.offer(new PowerToolData(command));
		target.setItemInHand(HandTypes.MAIN_HAND, hand);

		target.sendMessage(Text.of(TextColors.GOLD, "The command ", TextColors.RED, command, TextColors.GOLD, " was set to the item in your hand."));
	}

	@Override
	public String getPermission() {
		return "cc.powertool";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}