package me.mrdaniel.crucialcraft.commands.message;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PermissionCommand;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandHelpop extends PermissionCommand {

	public CommandHelpop(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void perform(final CommandSource src, final CommandContext args) {
		String message = args.<String>getOne("message").get();
		String name = src instanceof Player ? ((Player)src).getName() : "Console";
		Text msg = TextUtils.toText("&6[&2HelpOp&6] " + name + ": " + message);
		super.getCrucialCraft().getGame().getServer().getOnlinePlayers().forEach(p -> {
			if (p.hasPermission("cc.helpop.receive")) { p.sendMessage(msg); }
		});
	}

	@Override
	public String getPermission() {
		return "cc.helpop.send";
	}
}