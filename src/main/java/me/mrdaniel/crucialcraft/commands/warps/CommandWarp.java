package me.mrdaniel.crucialcraft.commands.warps;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.teleport.Teleport;

public class CommandWarp extends TargetSelfOrOtherPlayerCommand {

	public CommandWarp(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")), Argument.optional(Argument.string("name")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) throws CommandException {
		if (!args.has("name")) { super.getCrucialCraft().getGame().getCommandManager().process(target, "warp list"); return; }
		String name = args.get("name");

		Teleport teleport = super.getCrucialCraft().getDataFile().getWarp(name).orElseThrow(() -> new CommandException("No warp with that name exists."));

		if (teleport.teleport(super.getCrucialCraft(), target, Text.of(TextColors.GOLD, "You were teleported to warp ", TextColors.RED, name, TextColors.GOLD, "."), src.isPresent())) {
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to warp ", TextColors.RED, name, TextColors.GOLD, ".")));
		}
		else { throw new CommandException("This location doesnt exist anymore."); }
	}

	@Override
	public String getName() {
		return "Warp";
	}

	@Override
	public String getPermission() {
		return "cc.warp.tp";
	}
}