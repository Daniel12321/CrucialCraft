package me.mrdaniel.crucialcraft.commands;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetSelfOrOtherPlayerCommand;

public class CommandAntioch extends TargetSelfOrOtherPlayerCommand {

	public CommandAntioch(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.optional(Argument.player(cc, "target")));
	}

	@Override
	public void execute(final Optional<CommandSource> src, final Player target, final Arguments args) {
		PrimedTNT tnt = (PrimedTNT) target.getWorld().createEntity(EntityTypes.PRIMED_TNT, target.getLocation().getPosition());
		tnt.offer(Keys.FUSE_DURATION, 100);

		target.sendMessage(Text.of(TextColors.GOLD, "You got blown up!"));
	}

	@Override
	public String getPermission() {
		return "cc.antioch";
	}

	@Override
	public String getName() {
		return "Antioch";
	}
}