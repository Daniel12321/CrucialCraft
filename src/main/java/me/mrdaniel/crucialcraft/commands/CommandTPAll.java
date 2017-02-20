package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandTPAll extends PlayerCommand {

	public CommandTPAll(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final Arguments args) {
		Teleport tp = new Teleport(src.getLocation(), src.getHeadRotation());
		super.getCrucialCraft().getGame().getServer().getOnlinePlayers().forEach(p -> {
			if (!p.equals(src)) {
				if (!tp.teleport(super.getCrucialCraft(), p, Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, src.getName(), TextColors.GOLD, "'s location."), true)) {
					Messages.TELEPORT_DOESNT_EXIST.send(p);
				}
			}
		});
	}

	@Override
	public String getPermission() {
		return "cc.tpall";
	}

	@Override
	public String getName() {
		return "TPAll";
	}
}