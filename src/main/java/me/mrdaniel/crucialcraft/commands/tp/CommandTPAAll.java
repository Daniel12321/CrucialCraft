package me.mrdaniel.crucialcraft.commands.tp;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.teleport.TPRequest;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandTPAAll extends PlayerCommand {

	public CommandTPAAll(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player src, final CommandContext args) {
		super.getCrucialCraft().getGame().getServer().getOnlinePlayers().forEach(p -> {
			if (!p.equals(src)) {
				TPRequest req = new TPRequest(super.getCrucialCraft(), p.getUniqueId(), new Teleport(src.getLocation(), src.getHeadRotation()), Text.of(TextColors.GOLD, ""), Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getNick().orElse(Text.of(src.getName())), TextColors.GOLD, "'s location."));

				p.sendMessage(Text.of(TextColors.RED, super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getNick().orElse(Text.of(src.getName())), TextColors.GOLD, " requested you to teleport to him/her."));
				p.sendMessage(Text.of(TextColors.GOLD, "Click here to ", TextUtils.getAcceptText(req, src), TextColors.GOLD, " or to ", TextUtils.getDenyText(req), TextColors.GOLD, "."));
			}
		});
		src.sendMessage(Text.of(TextColors.GOLD, "You requested all online players to teleport to you."));
	}

	@Override
	public String getPermission() {
		return "cc.tpaall";
	}
}