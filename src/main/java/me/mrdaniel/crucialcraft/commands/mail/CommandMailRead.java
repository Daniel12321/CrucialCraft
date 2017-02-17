package me.mrdaniel.crucialcraft.commands.mail;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.PlayerCommand;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandMailRead extends PlayerCommand {

	public CommandMailRead(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final Player target, final CommandContext args) {
		List<String> mail = super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).getMail();
		if (mail.isEmpty()) { target.sendMessage(Text.of(TextColors.GOLD, "You dont havr any mail.")); return; }

		target.sendMessage(Text.of(TextColors.GOLD, "You have ", TextColors.RED, mail.size(), TextColors.GOLD, " new mails."));
		target.sendMessages(mail.stream().map(TextUtils::toText).collect(Collectors.toList()));
	}

	@Override
	public String getPermission() {
		return "cc.mail.read";
	}
}