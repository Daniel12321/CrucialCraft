//package me.mrdaniel.crucialcraft.commands.simple;
//
//import java.util.Iterator;
//import java.util.Optional;
//
//import javax.annotation.Nonnull;
//
//import org.spongepowered.api.block.BlockTypes;
//import org.spongepowered.api.command.CommandSource;
//import org.spongepowered.api.command.args.CommandContext;
//import org.spongepowered.api.entity.living.player.Player;
//import org.spongepowered.api.event.cause.NamedCause;
//import org.spongepowered.api.util.blockray.BlockRay;
//import org.spongepowered.api.util.blockray.BlockRayHit;
//import org.spongepowered.api.world.Location;
//import org.spongepowered.api.world.World;
//
//import me.mrdaniel.crucialcraft.CrucialCraft;
//import me.mrdaniel.crucialcraft.commands.TargetPlayerCommand;
//import me.mrdaniel.crucialcraft.utils.ServerUtils;
//
//public class CommandBreak extends TargetPlayerCommand {
//
//	public CommandBreak(@Nonnull final CrucialCraft cc) {
//		super(cc);
//	}
//
//	@Override
//	public void execute(final Player target, final Optional<CommandSource> src, final CommandContext args) {
//		Iterator<BlockRayHit<World>> iter = BlockRay.from(target).iterator();
//		while (iter.hasNext()) {
//			Location<World> loc = iter.next().getLocation();
//			if (loc.getBlockType() != BlockTypes.AIR) {
//				loc.getExtent().digBlock(loc.getBlockPosition(), ServerUtils.getCause(super.getCrucialCraft().getContainer(), NamedCause.simulated(target)));
//				return;
//			}
//		}
//	}
//
//	@Override
//	public String getPermission() {
//		return "cc.break";
//	}
//
//	@Override
//	public boolean canTargetSelf() {
//		return true;
//	}
//}