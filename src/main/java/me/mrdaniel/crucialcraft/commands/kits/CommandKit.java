package me.mrdaniel.crucialcraft.commands.kits;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;

public class CommandKit extends PlayerCommand {

	public CommandKit(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<String> kit = args.getOne("name");
		if (!kit.isPresent()) { super.getCrucialCraft().getGame().getCommandManager().process(target, "kits"); return; }

		//if () {}
	}

	@Override
	public String getPermission() {
		return "cc.kit.get";
	}
}