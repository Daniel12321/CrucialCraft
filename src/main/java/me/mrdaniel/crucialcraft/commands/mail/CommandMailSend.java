package me.mrdaniel.crucialcraft.commands.mail;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Argument;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.TargetUserCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;

public class CommandMailSend extends TargetUserCommand {

	public CommandMailSend(@Nonnull final CrucialCraft cc) {
		super(cc, Argument.user(cc, "target"), Argument.remaining("message"));
	}

	@Override
	public void execute(final CommandSource src, final User target, final Arguments args) throws CommandException {
		Optional<Player> p = target.getPlayer();
		String sender = src instanceof Player ? ((Player)src).getName() : "Console";
		String message = args.get("message");

		if (message.contains("&") && !src.hasPermission("cc.colors.mail")) { throw new CommandException("You dont have permission to use colors."); }

		if (p.isPresent()) {
			super.getCrucialCraft().getPlayerData().get(target.getUniqueId()).addMail(sender, message);
			p.get().sendMessage(Text.of(TextColors.GOLD, "You received mail! Click ", this.getOpenText(), TextColors.GOLD, " to open it."));
		}
		else {
			super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId()).orElseThrow(() -> new CommandException("No user with that name exists.")).addMail(sender, message);
		}
	}

	@Nonnull
	private Text getOpenText() {
		return Text.builder().append(Text.of(TextColors.RED, "here")).onHover(TextActions.showText(Text.of(TextColors.GOLD, "Click to open your mail."))).onClick(TextActions.runCommand("/mail read")).build();
	}

	@Override
	public String getPermission() {
		return "cc.mail.send";
	}

	@Override
	public String getName() {
		return "Mail Send";
	}
}