package me.mrdaniel.crucialcraft.commands.simple;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.data.PowerToolData;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandPowerTool extends PlayerCommand {

	public CommandPowerTool(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		String command = args.<String>getOne("command").get();

		if (!target.getItemInHand(HandTypes.MAIN_HAND).isPresent()) { Messages.NO_ITEM_IN_HAND.send(target); }

		ItemStack hand = target.getItemInHand(HandTypes.MAIN_HAND).get();

		if (command.equalsIgnoreCase("none") || command.equalsIgnoreCase("clear") || command.equalsIgnoreCase("remove") || command.equalsIgnoreCase("reset")) {
			hand.remove(PowerToolData.class);
			target.setItemInHand(HandTypes.MAIN_HAND, hand);
			target.sendMessage(Text.of(TextColors.GOLD, "Any powertool command was removed from the item in your hand."));
		}
		else {  
			hand.offer(new PowerToolData(command));
			target.setItemInHand(HandTypes.MAIN_HAND, hand);

			target.sendMessage(Text.of(TextColors.GOLD, "The command ", TextColors.RED, command, TextColors.GOLD, " was set to the item in your hand."));
		}
	}

	@Override
	public String getPermission() {
		return "cc.powertool";
	}
}