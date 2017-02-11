package me.mrdaniel.crucialcraft.utils;

import java.util.List;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.service.permission.Subject;

import com.google.common.collect.Lists;

public class PermissionsUtils {

	@Nonnull
	public static String getPrefix(@Nonnull final Player p) {
		return p.getContainingCollection().get(p.getIdentifier()).getOption("prefix").orElse("");
	}

	@Nonnull
	public static String getSuffix(@Nonnull final Player p) {
		return p.getContainingCollection().get(p.getIdentifier()).getOption("suffix").orElse("");
	}

	public static List<String> getGroups(@Nonnull final Player p) {

		List<String> groups = Lists.newArrayList();
    	Subject subject = p.getContainingCollection().get(p.getIdentifier());

		for (Subject group : subject.getParents()) {
			if (!groups.contains(group.getIdentifier().toLowerCase())) groups.add(group.getIdentifier().toLowerCase());
		}
		return groups;
	}

//	public static int getHomeLimit(@Nonnull final Player player) {
//    	Subject subject = player.getContainingCollection().get(player.getIdentifier());
//		String o = subject.getOption("homes").orElse("");
//		if (o.equalsIgnoreCase("") || o.equalsIgnoreCase("unlimited")) return Integer.MAX_VALUE;
//		try { return Integer.parseInt(o); }
//		catch(NumberFormatException e) { return 0; }
//	}
}