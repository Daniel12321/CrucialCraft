package me.mrdaniel.crucialcraft.commands.kits;

import java.util.List;
import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandKit extends PlayerCommand {

	public CommandKit(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		Optional<String> k = args.getOne("name");
		if (!k.isPresent()) { super.getCrucialCraft().getGame().getCommandManager().process(target, "kits"); return; }
		String kit = k.get();

		List<String> kits = super.getCrucialCraft().getKits().getKits();
		if (!kits.contains(kit)) { Messages.NO_SUCH_KIT.send(target); return; }

		if (!target.hasPermission("cc.kits." + kit)) { Messages.NO_KIT_PERMISSION.send(target); return; }

		boolean playtime = super.getCrucialCraft().getKits().isKitPlaytime(kit);

		long time = playtime ? super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).getCurrentPlaytime() : System.currentTimeMillis();
		long requiredtime = super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).getLastKitUse(kit) + super.getCrucialCraft().getKits().getKitDelay(kit) * 1000;

		if (target.hasPermission("cc.bypass.delay.kits") || time > requiredtime) {
			super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).setLastKitUse(kit, time);
			super.getCrucialCraft().getKits().getKit(kit).forEach(target.getInventory()::offer);
		}
		else { target.sendMessage(Text.of(TextColors.GOLD, "This kit will be available in ", TextColors.RED, TextUtils.getTimeFormat(requiredtime - time), TextColors.GOLD, playtime ? " ingame time." : ".")); }
	}

	@Override
	public String getPermission() {
		return "cc.kits.get";
	}
}