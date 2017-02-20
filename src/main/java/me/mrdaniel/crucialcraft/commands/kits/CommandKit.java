package me.mrdaniel.crucialcraft.commands.kits;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandKit extends PlayerCommand {

	public CommandKit(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.string("name")));
	}

	@Override
	public void execute(final Player src, final Arguments args) throws CommandException {
		if (!args.has("name")) { super.getCrucialCraft().getGame().getCommandManager().process(src, "kit list"); return; }
		String kit = args.get("name");

		List<String> kits = super.getCrucialCraft().getKits().getKits();
		if (!kits.contains(kit)) { throw new CommandException("No kit with that name exists."); }

		if (!src.hasPermission("cc.kits." + kit)) { throw new CommandException("You dont have permission to use that kit."); }

		boolean playtime = super.getCrucialCraft().getKits().isKitPlaytime(kit);

		long time = playtime ? super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getCurrentPlaytime() : System.currentTimeMillis();
		long requiredtime = super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getLastKitUse(kit) + super.getCrucialCraft().getKits().getKitDelay(kit) * 1000;

		if (src.hasPermission("cc.bypass.delay.kits") || time > requiredtime) {
			super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).setLastKitUse(kit, time);
			super.getCrucialCraft().getKits().getKit(kit).forEach(src.getInventory()::offer);
		}
		else { src.sendMessage(Text.of(TextColors.GOLD, "This kit will be available in ", TextColors.RED, TextUtils.getTimeFormat(requiredtime - time), TextColors.GOLD, playtime ? " ingame time." : ".")); }
	}

	@Override
	public String getPermission() {
		return "cc.kits.get";
	}

	@Override
	public String getName() {
		return "Kit";
	}
}