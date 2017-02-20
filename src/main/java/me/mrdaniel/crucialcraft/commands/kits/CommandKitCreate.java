package me.mrdaniel.crucialcraft.commands.kits;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;

public class CommandKitCreate extends PlayerCommand  {

	public CommandKitCreate(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.string("name"), Argument.integer("time-seconds"), Argument.bool("playtime"));
	}

	@Override
	public void execute(final Player target, final Arguments args) {
		String name = args.get("name");
		int seconds = args.get("time-seconds");
		boolean playtime = args.get("playtime");

		List<ItemStack> items = Lists.newArrayList();
		target.getInventory().slots().forEach(slot -> slot.peek().ifPresent(item -> items.add(item)));

		super.getCrucialCraft().getKits().setKit(name, items, seconds, playtime);
		target.sendMessage(Text.of(TextColors.GOLD, "You created kit ", TextColors.RED, name, TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.kits.create";
	}

	@Override
	public String getName() {
		return "Kit Create";
	}
}