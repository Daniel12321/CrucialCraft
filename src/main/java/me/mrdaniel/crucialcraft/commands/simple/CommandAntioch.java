package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;

public class CommandAntioch extends TargetPlayerCommand {

	public CommandAntioch(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		PrimedTNT tnt = (PrimedTNT) target.getWorld().createEntity(EntityTypes.PRIMED_TNT, target.getLocation().getPosition());
		tnt.offer(Keys.FUSE_DURATION, 100);

		target.sendMessage(Text.of(TextColors.GOLD, "You got blown up!"));
	}

	@Override
	public String getPermission() {
		return "cc.antioch";
	}

	@Override
	public boolean canTargetSelf() {
		return true;
	}
}