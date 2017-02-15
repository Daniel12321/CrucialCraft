package me.mrdaniel.crucialcraft.commands.tp;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandTPAll extends PlayerCommand {

	public CommandTPAll(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		Teleport tp = new Teleport(target.getLocation(), target.getHeadRotation());
		super.getCrucialCraft().getGame().getServer().getOnlinePlayers().forEach(p -> {
			if (!p.equals(target)) {
				if (tp.teleport(super.getCrucialCraft(), p)) { p.sendMessage(Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, target.getName(), TextColors.GOLD, "'s location.")); }
			}
		});
	}

	@Override
	public String getPermission() {
		return "cc.tpall";
	}
}