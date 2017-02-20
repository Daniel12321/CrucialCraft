package me.mrdaniel.crucialcraft.commands;

import javax.annotation.Nonnull;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.Arguments;
import me.mrdaniel.crucialcraft.command.SimpleCommand;
import me.mrdaniel.crucialcraft.command.exception.CommandException;
import me.mrdaniel.crucialcraft.utils.TextUtils;

public class CommandLag extends SimpleCommand {

	public CommandLag(@Nonnull final CrucialCraft cc) {
		super(cc);
	}

	@Override
	public void execute(final CommandSource src, final Arguments args) throws CommandException {
		Runtime runtime = Runtime.getRuntime();

		double tps = super.getCrucialCraft().getGame().getServer().getTicksPerSecond();
		String tps_string = String.valueOf(tps);
		if (tps_string.length() > 6) { tps_string = tps_string.substring(0, 7); }
		long max_memory = runtime.maxMemory() / (1024 * 1024);
		long free_memory = runtime.freeMemory() / (1024 * 1024);
		long allocated_memory = max_memory - free_memory;

		double available_percent = free_memory * 100 / max_memory;

		src.sendMessage(Text.of(TextColors.GOLD, "Uptime: ", TextColors.RED, TextUtils.getTimeFormat(System.currentTimeMillis() - super.getCrucialCraft().getStartupTime())));
		src.sendMessage(Text.of(TextColors.GOLD, "TPS: ", tps > 16 ? TextColors.GREEN : tps > 10 ? TextColors.YELLOW : TextColors.DARK_RED, tps_string));
		src.sendMessage(Text.of(TextColors.GOLD, "Memory Usage: ", available_percent < 80 ? TextColors.GREEN : available_percent < 90 ? TextColors.YELLOW : TextColors.DARK_RED, allocated_memory, " MB / ", max_memory, " MB"));
		super.getCrucialCraft().getGame().getServer().getWorlds().forEach(world -> src.sendMessage(Text.of(TextColors.GOLD, "World \"", TextColors.RED, world.getName(), TextColors.GOLD, "\": ", TextColors.RED, world.getLoadedChunks().spliterator().estimateSize(), TextColors.GOLD, " chunks, ", TextColors.RED, world.getTileEntities().size(), TextColors.GOLD, " tiles, ", TextColors.RED, world.getEntities().size(), TextColors.GOLD, " entities.")));
	}

	@Override
	public String getName() {
		return "Lag";
	}

	@Override
	public String getPermission(final Arguments args) {
		return "cc.lag";
	}
}