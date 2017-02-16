package me.mrdaniel.crucialcraft.commands.message;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.Messages;
import me.mrdaniel.crucialcraft.utils.ServerUtils;

public class CommandMe extends PermissionCommand {

	public CommandMe(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		if (src instanceof Player && super.getCrucialCraft().getPlayerData().get(((Player)src).getUniqueId()).isMuted()) { Messages.MUTED.send(src); return; }

		String message = args.<String>getOne("message").get();

		Text name = (src instanceof Player) ? super.getCrucialCraft().getPlayerData().get(((Player)src).getUniqueId()).getNick().orElse(Text.of(((Player)src).getName())) : Text.of("Console") ;
		ServerUtils.broadcast(super.getCrucialCraft().getGame().getServer(), Text.of(TextColors.DARK_PURPLE, "* ", name, " ", TextColors.DARK_PURPLE, message));
	}

	@Override
	public String getPermission() {
		return "cc.me";
	}
}