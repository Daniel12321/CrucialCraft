package me.mrdaniel.crucialcraft.commands.warp;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandWarp extends PlayerCommand {

	public CommandWarp(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<String> n = args.<String>getOne("name");

		if (!n.isPresent()) { super.getCrucialCraft().getGame().getCommandManager().process(target, "warps"); return; }
		String name = n.get();

		Optional<Teleport> tp = super.getCrucialCraft().getDataFile().getWarp(name);
		if (!tp.isPresent()) { Messages.NO_SUCH_WARP.send(src.orElse(target)); return; }
		Teleport teleport = tp.get();

		CCPlayerData data = target.get(CCPlayerData.class).get();
		data.setLastLocation(new Teleport(target.getLocation(), target.getHeadRotation()));
		target.offer(data);

		if (teleport.teleport(super.getCrucialCraft(), target)) {
			target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to warp ", TextColors.RED, name, TextColors.GOLD, "."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to warp ", TextColors.RED, name, TextColors.GOLD, ".")));
		}
		else { Messages.TELEPORT_DOESNT_EXIST.send(src.orElse(target)); }
	}

	@Override
	public String getPermission() {
		return "cc.warp.tp";
	}
}