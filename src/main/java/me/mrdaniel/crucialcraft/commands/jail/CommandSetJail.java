package me.mrdaniel.crucialcraft.commands.jail;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.data.Teleport;

public class CommandSetJail extends PlayerCommand {

	public CommandSetJail(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		String name = args.<String>getOne("name").get();
		super.getCrucialCraft().getDataFile().setJail(name, new Teleport(target.getLocation(), target.getHeadRotation()));
		target.sendMessage(Text.of(TextColors.GOLD, "You set jail ", TextColors.RED, name, TextColors.GOLD, " to your location."));
	}

	@Override
	public String getPermission() {
		return "cc.jail.set";
	}
}