package me.mrdaniel.crucialcraft.commands.tp;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerTargetPlayerCommand;
import me.mrdaniel.crucialcraft.teleport.TPRequest;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandTPA extends PlayerTargetPlayerCommand{

	public CommandTPA(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Player src, final CommandContext args) {
		TPRequest req = new TPRequest(super.getCrucialCraft(), src.getUniqueId(), new Teleport(target.getLocation(), target.getHeadRotation()), Text.of(TextColors.GOLD, ""), Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).getNick().orElse(Text.of(target.getName())), TextColors.GOLD, "'s location."));

		target.sendMessage(Text.of(TextColors.RED, super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getNick().orElse(Text.of(src.getName())), TextColors.GOLD, " requested to teleport to you."));
		target.sendMessage(Text.of(TextColors.GOLD, "Click here to ", TextUtils.getAcceptText(req, src), TextColors.GOLD, " or to ", TextUtils.getDenyText(req), TextColors.GOLD, "."));
		src.sendMessage(Text.of(TextColors.GOLD, "You send a teleport request to ", TextColors.RED, super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).getNick().orElse(Text.of(target.getName())), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.tpa";
	}

	@Override
	public boolean canTargetSelf() {
		return false;
	}
}