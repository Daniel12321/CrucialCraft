//package me.mrdaniel.crucialcraft.commands;
//
//import java.util.Optional;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.command.CommandSource;
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.entity.living.player.User;
//import org.spongepowered.api.profile.GameProfile;
//import org.spongepowered.api.text.Text;
//import org.spongepowered.api.text.format.TextColors;
//import org.spongepowered.api.util.ban.Ban;
//
//import me.mrdaniel.crucialcraft.CrucialCraft;
//import me.mrdaniel.crucialcraft.command.Argument;
//import me.mrdaniel.crucialcraft.command.Arguments;
//import me.mrdaniel.crucialcraft.command.TargetUserCommand;
//import me.mrdaniel.crucialcraft.command.exception.CommandException;
//import me.mrdaniel.crucialcraft.utils.TextUtils;
//
//public class CommandBan extends TargetUserCommand {
//
//	public CommandBan(@Nonnull final CrucialCraft cc) {
//		super(cc, Argument.user(cc, "target"), Argument.optional(Argument.remaining("reason")));
//	}
//
//	@Override
//	public void execute(final CommandSource src, final User target, final Arguments args) throws CommandException {
//		String name = src instanceof Player ? ((Player)src).getName() : "a server administrator";
//		Text reason = Text.of(TextColors.RED, "You were banned from the server by ", name, ".", args.has("reason") ? " Reason: " + args.get("reason") : "");
//		Optional<Player> p = target.getPlayer();
//		if (p.isPresent()) {
//			String msg = "You were banned from the server by " + name + ".";
//			if (args.has("reason")) { p.get().kick(TextUtils.toText("&c" + msg + " Reason: " + args.get("reason"))); }
//			else { p.get().kick(Text.of(TextColors.RED, "msg")); }
//		}
//		super.getCrucialCraft().getBans().addBan(Ban.builder().profile(super.getCrucialCraft()).source(Text.of(name)).reason(reason).build());
//	}
//
//	@Override
//	public String getPermission() {
//		return "cc.ban";
//	}
//
//	@Override
//	public String getName() {
//		return "Ban";
//	}
//}