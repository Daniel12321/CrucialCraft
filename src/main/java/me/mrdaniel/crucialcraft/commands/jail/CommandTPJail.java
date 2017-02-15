package me.mrdaniel.crucialcraft.commands.jail;

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

public class CommandTPJail extends TargetPlayerCommand {

	public CommandTPJail(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		String name = args.<String>getOne("name").get();
		Optional<Teleport> jail = super.getCrucialCraft().getDataFile().getJail(name);
		if (!jail.isPresent()) { Messages.NO_SUCH_JAIL.send(src.orElse(target)); return; }
		Teleport tp = jail.get();

		if (tp.teleport(super.getCrucialCraft(), target)) {
			target.sendMessage(Text.of(TextColors.GOLD, "You were teleported to jail ", TextColors.RED, name, TextColors.GOLD, "."));
			src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You teleported ", TextColors.RED, target.getName(), TextColors.GOLD, " to jail ", TextColors.RED, name, TextColors.GOLD, ".")));
		}
		else { Messages.TELEPORT_DOESNT_EXIST.send(src.orElse(target)); }
	}

	@Override
	public String getPermission() {
		return "cc.jail.tp";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}