package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.ProjectileUtils;

public class CommandSnowball extends PlayerCommand {

	public CommandSnowball(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final Arguments args) {
		ProjectileUtils.launchProjectile(target, Snowball.class, 7.5);
		target.sendMessage(Text.of(TextColors.GOLD, "You launched a snowball."));
	}

	@Override
	public String getPermission() {
		return "cc.snowball";
	}

	@Override
	public String getName() {
		return "Snowball";
	}
}