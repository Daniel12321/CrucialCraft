package me.mrdaniel.crucialcraft.commands.simple;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.ProjectileUtils;

public class CommandSnowball extends PlayerCommand {

	public CommandSnowball(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		ProjectileUtils.launchProjectile(target, Snowball.class, 7.5);
		target.sendMessage(Text.of(TextColors.GOLD, "You launched a snowball."));
	}

	@Override
	public String getPermission() {
		return "cc.snowball";
	}
}