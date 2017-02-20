package me.mrdaniel.crucialcraft.commands.jail;

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

public class CommandTPJail extends TargetSelfOrOtherPlayerCommand {

	public CommandTPJail(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")), Argument.string("name"));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) throws CommandException {
		String name = args.get("name");
		Teleport jail = super.getCrucialCraft().getDataFile().getJail(name).orElseThrow(() -> new CommandException("No jail with that name exists."));

		if (jail.teleport(super.getCrucialCraft(), target, Text.of(TextColors.GOLD, "You were teleported to jail ", TextColors.RED, name, TextColors.GOLD, "."), false)) {
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to jail ", TextColors.RED, name, TextColors.GOLD, ".")));
		}
		else { throw new CommandException("This location doesnt exist anymore."); }
	}

	@Override
	public String getPermission() {
		return "cc.jail.tp";
	}

	@Override
	public String getName() {
		return "Jail TP";
	}
}