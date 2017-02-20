package me.mrdaniel.crucialcraft.command;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.living.player.gamemode.GameMode;
import org.spongepowered.api.entity.living.player.gamemode.GameModes;
import org.spongepowered.api.item.Enchantment;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.weather.Weather;
import org.spongepowered.api.world.weather.Weathers;

import com.google.common.collect.Lists;

import me.mrdaniel.crucialcraft.CrucialCraft;
import me.mrdaniel.crucialcraft.command.exception.ArgumentException;

public abstract class Argument {

	private final String key;

	private Argument(@Nonnull final String key) {
		this.key = key;
	}

	@Nonnull public String getKey() { return this.key; }

	public abstract Object translate(@Nonnull final List<String> args) throws ArgumentException;
	public abstract List<String> getChoices();
	public abstract String getUsage();


	public static Argument optional(@Nonnull final Argument arg) {
		return new Argument(arg.key) {
			@Override public Object translate(@Nonnull final List<String> args) {
				try { return arg.translate(args); }
				catch (final ArgumentException exc) { return null; }
			}	
			@Override public List<String> getChoices() { return arg.getChoices(); }
			@Override public String getUsage() { return "[" + arg.getUsage().replace("<", "").replace(">", "") + "]"; }
		};
	}

	public static Argument bool(@Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				String str = args.get(0);
				boolean bool;
				if (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("y")) { bool = true; }
				else if (str.equalsIgnoreCase("false") || str.equalsIgnoreCase("no") || str.equalsIgnoreCase("n")) { bool = false; }
				else throw new ArgumentException("Invalid boolean format.");
				args.remove(0);
				return bool;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList("true", "yes", "y", "false", "no", "n"); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument string(@Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				String str = args.get(0);
				args.remove(0);
				return str;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList(); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument integer(@Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				int i;
				try { i = Integer.parseInt(args.get(0)); }
				catch (final NumberFormatException exc) { throw new ArgumentException(""); }
				args.remove(0);
				return i;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList(); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument doubleNum(@Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				double i;
				try { i = Double.parseDouble(args.get(0)); }
				catch (final NumberFormatException exc) { throw new ArgumentException(""); }
				args.remove(0);
				return i;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList(); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument itemtype(@Nonnull final CrucialCraft cc, @Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				ItemType type = Optional.ofNullable(cc.getChoiceMaps().getItemTypes().get(args.get(0))).orElseThrow(() -> new ArgumentException("No item with that name exists."));
				args.remove(0);
				return type;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList(cc.getChoiceMaps().getItemTypes().keySet()); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument enchantment(@Nonnull final CrucialCraft cc, @Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				Enchantment type = Optional.ofNullable(cc.getChoiceMaps().getEnchantments().get(args.get(0))).orElseThrow(() -> new ArgumentException("No enchantment with that name exists."));
				args.remove(0);
				return type;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList(cc.getChoiceMaps().getEnchantments().keySet()); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument entitytype(@Nonnull final CrucialCraft cc, @Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				EntityType type = Optional.ofNullable(cc.getChoiceMaps().getEnityTypes().get(args.get(0))).orElseThrow(() -> new ArgumentException("No entity type with that name exists."));
				args.remove(0);
				return type;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList(cc.getChoiceMaps().getEnityTypes().keySet()); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument gamemode(@Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				String str = args.get(0).toLowerCase();
				GameMode gm;
				if (str.equals("0") || str.startsWith("su")) { gm = GameModes.SURVIVAL; }
				else if (str.equals("1") || str.startsWith("c")) { gm = GameModes.CREATIVE; }
				else if (str.equals("2") || str.startsWith("a")) { gm = GameModes.ADVENTURE; }
				else if (str.equals("3") || str.startsWith("sp")) { gm = GameModes.SPECTATOR; }
				else throw new ArgumentException("No gamemode with that name exists.");
				args.remove(0);
				return gm;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList("0", "1", "2", "3"); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument time(@Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				String str = args.get(0).toLowerCase();
				long time;
				if (str.equalsIgnoreCase("d") || str.equalsIgnoreCase("day")) { time = 0; }
				else if (str.startsWith("n")) { time = 14000; }
				else if (str.startsWith("du")) { time = 12000; }
				else if (str.startsWith("da")) { time = 22000; }
				else {
					try { time = Long.parseLong(str); }
					catch (final NumberFormatException exc) { throw new ArgumentException("No time with that name exists."); }
				}
				args.remove(0);
				return time;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList("day", "night", "dusk", "dawn"); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument weather(@Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				String str = args.get(0).toLowerCase();
				Weather w;
				if (str.startsWith("s") || str.startsWith("c")) { w = Weathers.CLEAR; }
				else if (str.startsWith("r")) { w = Weathers.RAIN; }
				else if (str.startsWith("t") || str.startsWith("l")) { w = Weathers.THUNDER_STORM; }
				else throw new ArgumentException("No weather with that name exists.");
				args.remove(0);
				return w;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList("clear", "rain", "thunder"); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

//	public static Argument currency(@Nonnull final CrucialCraft cc, @Nonnull final String key) {
//		return new Argument(key) {
//			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
//				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
//				String str = args.get(0).toLowerCase();
//				if (!cc.getConfig().getCurrencies().contains(str)) { throw new ArgumentException("No currency with that name exists."); }
//				args.remove(0);
//				return str;
//			}
//			@Override public List<String> getChoices() { return Lists.newArrayList("clear", "rain", "thunder"); }
//			@Override public String getUsage() { return "<" + key + ">"; }
//		};
//	}

	public static Argument world(@Nonnull final CrucialCraft cc, @Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				World w = cc.getGame().getServer().getWorld(args.get(0)).orElseThrow(() -> new ArgumentException("No world with that name exists."));
				args.remove(0);
				return w;
			}
			@Override public List<String> getChoices() { return cc.getGame().getServer().getWorlds().stream().map(w -> w.getName()).collect(Collectors.toList()); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument player(@Nonnull final CrucialCraft cc, @Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				Player p = cc.getGame().getServer().getPlayer(args.get(0)).orElseThrow(() -> new ArgumentException("No player with that name exists."));
				args.remove(0);
				return p;
			}
			@Override public List<String> getChoices() { return cc.getGame().getServer().getOnlinePlayers().stream().map(p -> p.getName()).collect(Collectors.toList()); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument user(@Nonnull final CrucialCraft cc, @Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				User u = cc.getUserStorage().get(args.get(0)).orElseThrow(() -> new ArgumentException("No user with that name exists."));
				args.remove(0);
				return u;
			}
			@Override public List<String> getChoices() { return cc.getUserStorage().getAll().stream().map(cc.getUserStorage()::get).filter(u -> u.isPresent()).map(u -> u.get().getName()).collect(Collectors.toList()); }
			@Override public String getUsage() { return "<" + key + ">"; }
		};
	}

	public static Argument remaining(@Nonnull final String key) {
		return new Argument(key) {
			@Override public Object translate(@Nonnull final List<String> args) throws ArgumentException {
				if (args.isEmpty()) { throw new ArgumentException("Not enough arguments."); }
				String str = args.get(0);
				for (int i = 1; i < args.size(); i++) { str += " " + args.get(i); }
				args.clear();
				return str;
			}
			@Override public List<String> getChoices() { return Lists.newArrayList(); }
			@Override public String getUsage() { return "<" + key + "...>"; }
		};
	}
}