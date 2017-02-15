package me.mrdaniel.crucialcraft.commands.warp;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandWarp extends TargetPlayerCommand {

	public CommandWarp(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<String> n = args.<String>getOne("name");

		if (!n.isPresent()) { super.getCrucialCraft().getGame().getCommandManager().process(target, "warps"); return; }
		String name = n.get();

		Optional<Teleport> tp = super.getCrucialCraft().getDataFile().getWarp(name);
		if (!tp.isPresent()) { Messages.NO_SUCH_WARP.send(src.orElse(target)); return; }
		Teleport teleport = tp.get();

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

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}