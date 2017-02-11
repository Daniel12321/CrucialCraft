package me.mrdaniel.crucialcraft.commands.warp;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandDelWarp extends PermissionCommand {

	public CommandDelWarp(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		String name = args.<String>getOne("name").get();
		if (!super.getCrucialCraft().getDataFile().getWarp(name).isPresent()) {	Messages.NO_SUCH_WARP.send(src); return; }

		super.getCrucialCraft().getDataFile().setWarp(name, null);
		src.sendMessage(Text.of(TextColors.GOLD, "You deleted warp ", TextColors.RED, name, TextColors.GOLD, "."));
	}

	@Override
	public String getPermission() {
		return "cc.warp.del";
	}
}