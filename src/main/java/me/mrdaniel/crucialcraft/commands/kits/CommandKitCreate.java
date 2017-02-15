package me.mrdaniel.crucialcraft.commands.kits;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;

public class CommandKitCreate extends PlayerCommand  {

	public CommandKitCreate(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		String name = args.<String>getOne("name").get();
		int seconds = args.<Integer>getOne("time-seconds").get();
		boolean playtime = args.<Boolean>getOne("playtime").get();

		List<ItemStack> items = Lists.newArrayList();
		target.getInventory().slots().forEach(slot -> slot.peek().ifPresent(item -> items.add(item)));

		super.getCrucialCraft().getKits().setKit(name, items, seconds, playtime);
		target.sendMessage(Text.of(TextColors.GOLD, "You created kit ", TextColors.RED, name, TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.kits.create";
	}
}