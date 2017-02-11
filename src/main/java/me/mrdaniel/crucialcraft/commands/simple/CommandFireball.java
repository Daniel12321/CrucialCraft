package me.mrdaniel.crucialcraft.commands.simple;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.explosive.fireball.LargeFireball;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.ProjectileUtils;

public class CommandFireball extends PlayerCommand {

	public CommandFireball(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final Player target, final Optional<CommandSource> src, final CommandContext args) {
		ProjectileUtils.launchProjectile(target, LargeFireball.class, 3);
		src.ifPresent(s -> s.sendMessage(Text.of(TextColors.GOLD, "You made ", TextColors.RED, target.getName(), TextColors.GOLD, " shoot a fireball.")));
	}

	@Override
	public String getPermission() {
		return "cc.fireball";
	}
}