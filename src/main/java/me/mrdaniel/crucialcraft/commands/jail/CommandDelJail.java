package me.mrdaniel.crucialcraft.commands.jail;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.teleport.Teleport;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandDelJail extends PermissionCommand {

	public CommandDelJail(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		String name = args.<String>getOne("name").get();
		Optional<Teleport> jail = super.getCrucialCraft().getDataFile().getJail(name);
		if (!jail.isPresent()) { Messages.NO_SUCH_JAIL.send(src); return; }

		super.getCrucialCraft().getDataFile().setJail(name, null);
		src.sendMessage(Text.of(TextColors.GOLD, "You deleted jail ", TextColors.RED, name, TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.jail.del";
	}
}