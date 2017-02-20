package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

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
import me.mrdaniel.crucialcraft.data.PowerToolData;

public class CommandPowerTool extends PlayerCommand {

	public CommandPowerTool(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.remaining("command"));
	}

	@Override
	public void execute(final Player target, final Arguments args) throws CommandException {
		String command = args.get("command");

		ItemStack hand = target.getItemInHand(HandTypes.MAIN_HAND).orElseThrow(() -> new CommandException("You are not holding an item."));

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

	@Override
	public String getName() {
		return "Powertool";
	}
}