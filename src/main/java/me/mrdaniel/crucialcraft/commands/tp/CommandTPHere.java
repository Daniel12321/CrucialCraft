package me.mrdaniel.crucialcraft.commands.tp;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerTargetPlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;

public class CommandTPHere extends PlayerTargetPlayerCommand {

	public CommandTPHere(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Player src, final CommandContext args) {
		CCPlayerData data = target.get(CCPlayerData.class).get();
		data.setLastLocation(new Teleport(target.getLocation(), target.getHeadRotation()));
		target.offer(data);

		target.setLocationAndRotation(src.getLocation(), src.getHeadRotation());
		target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, src.getName(), TextColors.GOLD, "'s location."));
		src.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to your location."));
	}

	@Override
	public String getPermission() {
		return "cc.tphere";
	}

	@Override
	public boolean canTargetSelf() {
		return false;
	}
}