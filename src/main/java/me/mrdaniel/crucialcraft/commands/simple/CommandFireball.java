package me.mrdaniel.crucialcraft.commands.simple;

import javax.annotation.Nonnull;

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
	public void execute(final Player target, final CommandContext args) {
		ProjectileUtils.launchProjectile(target, LargeFireball.class, 3);
		target.sendMessage(Text.of(TextColors.GOLD, "You launched a fireball."));
	}

	@Override
	public String getPermission() {
		return "cc.fireball";
	}
}