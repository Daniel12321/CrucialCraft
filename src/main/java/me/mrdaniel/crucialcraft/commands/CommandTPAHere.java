package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerTargetOtherPlayerCommand;
import me.mrdaniel.crucialcraft.teleport.TPRequest;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandTPAHere extends PlayerTargetOtherPlayerCommand {

	public CommandTPAHere(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.player(cc, "target"));
	}

	@Override
	public void execute(final Player src, final Player target, final Arguments args) {
		TPRequest req = new TPRequest(super.getCrucialCraft(), target.getUniqueId(), new Teleport(src.getLocation(), src.getHeadRotation()), Text.of(TextColors.GOLD, ""), Text.of(TextColors.GOLD, "You were teleported to ", TextColors.RED, super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getNick().orElse(Text.of(src.getName())), TextColors.GOLD, "'s location."));

		target.sendMessage(Text.of(TextColors.RED, super.getCrucialCraft().getPlayerData().get(src.getUniqueId()).getNick().orElse(Text.of(src.getName())), TextColors.GOLD, " requested you to teleport to him/her."));
		target.sendMessage(Text.of(TextColors.GOLD, "Click here to ", TextUtils.getAcceptText(req, src), TextColors.GOLD, " or to ", TextUtils.getDenyText(req), TextColors.GOLD, "."));
		src.sendMessage(Text.of(TextColors.GOLD, "You send a teleport request to ", TextColors.RED, super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).getNick().orElse(Text.of(target.getName())), TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.tpahere";
	}

	@Override
	public String getName() {
		return "TPAHere";
	}
}