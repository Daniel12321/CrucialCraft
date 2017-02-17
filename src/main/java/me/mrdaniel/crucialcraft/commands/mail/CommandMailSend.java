package me.mrdaniel.crucialcraft.commands.mail;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.commands.TargetUserCommand;
import me.mrdaniel.crucialcraft.io.PlayerFile;
import me.mrdaniel.crucialcraft.utils.Messages;

public class CommandMailSend extends TargetUserCommand {

	public CommandMailSend(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final User target, final Optional<CommandSource> src, final CommandContext args) {
		Optional<Player> p = target.getPlayer();
		String sender = src.get() instanceof Player ? ((Player)src.get()).getName() : "Console";
		String message = args.<String>getOne("message").get();

		if (message.contains("&") && !src.orElse(p.get()).hasPermission("cc.colors.mail")) { Messages.NO_COLOR_PERMISSION.send(src.orElse(p.get())); return; }

		if (p.isPresent()) {
			PlayerFile file = super.getCrucialCraft().getPlayerData().get(target.getUniqueId());
			file.addMail(sender, message);
			p.get().sendMessage(Text.of(TextColors.GOLD, "You received mail! Click ", this.getOpenText(), TextColors.GOLD, " to open it."));
		}
		else {
			Optional<PlayerFile> file = super.getCrucialCraft().getPlayerData().getOffline(target.getUniqueId());
			if (!file.isPresent()) { Messages.NO_SUCH_USER.send(src); return; }
			file.get().addMail(sender, message);
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
	public boolean canTargetSelf() {
		return false;
	}
}