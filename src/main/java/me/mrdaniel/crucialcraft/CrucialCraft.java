package me.mrdaniel.crucialcraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GamePostInitializationEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.service.ChangeServiceProviderEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.service.ban.BanService;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.service.whitelist.WhitelistService;

import me.mrdaniel.crucialcraft.command.CommandTree;
import me.mrdaniel.crucialcraft.commands.CommandAntioch;
import me.mrdaniel.crucialcraft.commands.CommandBack;
import me.mrdaniel.crucialcraft.commands.CommandBroadcast;
import me.mrdaniel.crucialcraft.commands.CommandBurn;
import me.mrdaniel.crucialcraft.commands.CommandButcher;
import me.mrdaniel.crucialcraft.commands.CommandClearInventory;
import me.mrdaniel.crucialcraft.commands.CommandDispose;
import me.mrdaniel.crucialcraft.commands.CommandEnchant;
import me.mrdaniel.crucialcraft.commands.CommandEnderchest;
import me.mrdaniel.crucialcraft.commands.CommandFeed;
import me.mrdaniel.crucialcraft.commands.CommandFireball;
import me.mrdaniel.crucialcraft.commands.CommandFly;
import me.mrdaniel.crucialcraft.commands.CommandGamemode;
import me.mrdaniel.crucialcraft.commands.CommandGive;
import me.mrdaniel.crucialcraft.commands.CommandGod;
import me.mrdaniel.crucialcraft.commands.CommandHat;
import me.mrdaniel.crucialcraft.commands.CommandHeal;
import me.mrdaniel.crucialcraft.commands.CommandHelpop;
import me.mrdaniel.crucialcraft.commands.CommandInvsee;
import me.mrdaniel.crucialcraft.commands.CommandItem;
import me.mrdaniel.crucialcraft.commands.CommandItemlore;
import me.mrdaniel.crucialcraft.commands.CommandItemname;
import me.mrdaniel.crucialcraft.commands.CommandJump;
import me.mrdaniel.crucialcraft.commands.CommandKick;
import me.mrdaniel.crucialcraft.commands.CommandKickall;
import me.mrdaniel.crucialcraft.commands.CommandKill;
import me.mrdaniel.crucialcraft.commands.CommandKillall;
import me.mrdaniel.crucialcraft.commands.CommandLag;
import me.mrdaniel.crucialcraft.commands.CommandList;
import me.mrdaniel.crucialcraft.commands.CommandMOTD;
import me.mrdaniel.crucialcraft.commands.CommandMe;
import me.mrdaniel.crucialcraft.commands.CommandMessage;
import me.mrdaniel.crucialcraft.commands.CommandMore;
import me.mrdaniel.crucialcraft.commands.CommandMute;
import me.mrdaniel.crucialcraft.commands.CommandNear;
import me.mrdaniel.crucialcraft.commands.CommandNick;
import me.mrdaniel.crucialcraft.commands.CommandPing;
import me.mrdaniel.crucialcraft.commands.CommandPlaytime;
import me.mrdaniel.crucialcraft.commands.CommandPowerTool;
import me.mrdaniel.crucialcraft.commands.CommandRealname;
import me.mrdaniel.crucialcraft.commands.CommandRepair;
import me.mrdaniel.crucialcraft.commands.CommandReply;
import me.mrdaniel.crucialcraft.commands.CommandRules;
import me.mrdaniel.crucialcraft.commands.CommandSeen;
import me.mrdaniel.crucialcraft.commands.CommandSnowball;
import me.mrdaniel.crucialcraft.commands.CommandSpawnMob;
import me.mrdaniel.crucialcraft.commands.CommandSpawner;
import me.mrdaniel.crucialcraft.commands.CommandSpeed;
import me.mrdaniel.crucialcraft.commands.CommandStop;
import me.mrdaniel.crucialcraft.commands.CommandSudo;
import me.mrdaniel.crucialcraft.commands.CommandSuicide;
import me.mrdaniel.crucialcraft.commands.CommandTP;
import me.mrdaniel.crucialcraft.commands.CommandTPA;
import me.mrdaniel.crucialcraft.commands.CommandTPAAll;
import me.mrdaniel.crucialcraft.commands.CommandTPAHere;
import me.mrdaniel.crucialcraft.commands.CommandTPAll;
import me.mrdaniel.crucialcraft.commands.CommandTPHere;
import me.mrdaniel.crucialcraft.commands.CommandTPPos;
import me.mrdaniel.crucialcraft.commands.CommandTime;
import me.mrdaniel.crucialcraft.commands.CommandTop;
import me.mrdaniel.crucialcraft.commands.CommandUnbreakable;
import me.mrdaniel.crucialcraft.commands.CommandUnmute;
import me.mrdaniel.crucialcraft.commands.CommandVanish;
import me.mrdaniel.crucialcraft.commands.CommandWeather;
import me.mrdaniel.crucialcraft.commands.CommandWhois;
import me.mrdaniel.crucialcraft.commands.CommandWorkbench;
import me.mrdaniel.crucialcraft.commands.exp.CommandExp;
import me.mrdaniel.crucialcraft.commands.exp.CommandExpAdd;
import me.mrdaniel.crucialcraft.commands.exp.CommandExpSet;
import me.mrdaniel.crucialcraft.commands.homes.CommandDelHome;
import me.mrdaniel.crucialcraft.commands.homes.CommandHome;
import me.mrdaniel.crucialcraft.commands.homes.CommandHomes;
import me.mrdaniel.crucialcraft.commands.homes.CommandSetHome;
import me.mrdaniel.crucialcraft.commands.jail.CommandDelJail;
import me.mrdaniel.crucialcraft.commands.jail.CommandJail;
import me.mrdaniel.crucialcraft.commands.jail.CommandJails;
import me.mrdaniel.crucialcraft.commands.jail.CommandSetJail;
import me.mrdaniel.crucialcraft.commands.jail.CommandTPJail;
import me.mrdaniel.crucialcraft.commands.jail.CommandUnjail;
import me.mrdaniel.crucialcraft.commands.kits.CommandKit;
import me.mrdaniel.crucialcraft.commands.kits.CommandKitCreate;
import me.mrdaniel.crucialcraft.commands.kits.CommandKits;
import me.mrdaniel.crucialcraft.commands.mail.CommandMailClear;
import me.mrdaniel.crucialcraft.commands.mail.CommandMailRead;
import me.mrdaniel.crucialcraft.commands.mail.CommandMailSend;
import me.mrdaniel.crucialcraft.commands.spawn.CommandDelNewbieSpawn;
import me.mrdaniel.crucialcraft.commands.spawn.CommandSetNewbieSpawn;
import me.mrdaniel.crucialcraft.commands.spawn.CommandSetSpawn;
import me.mrdaniel.crucialcraft.commands.spawn.CommandSpawn;
import me.mrdaniel.crucialcraft.commands.spawn.CommandTPNewbieSpawn;
import me.mrdaniel.crucialcraft.commands.warps.CommandDelWarp;
import me.mrdaniel.crucialcraft.commands.warps.CommandSetWarp;
import me.mrdaniel.crucialcraft.commands.warps.CommandWarp;
import me.mrdaniel.crucialcraft.commands.warps.CommandWarps;
import me.mrdaniel.crucialcraft.data.ImmutablePowerToolData;
import me.mrdaniel.crucialcraft.data.PowerToolData;
import me.mrdaniel.crucialcraft.data.PowerToolDataBuilder;
import me.mrdaniel.crucialcraft.io.Config;
import me.mrdaniel.crucialcraft.io.DataFile;
import me.mrdaniel.crucialcraft.io.HoconPlayerDataManager;
import me.mrdaniel.crucialcraft.io.Kits;
import me.mrdaniel.crucialcraft.io.PlayerDataManager;
import me.mrdaniel.crucialcraft.listeners.ChatListener;
import me.mrdaniel.crucialcraft.listeners.ClientListener;
import me.mrdaniel.crucialcraft.listeners.PlayerListener;
import me.mrdaniel.crucialcraft.listeners.WorldListener;
import me.mrdaniel.crucialcraft.teleport.TeleportManager;
import me.mrdaniel.crucialcraft.utils.ChoiceMaps;

@Plugin(id = "crucialcraft",
	name = "CrucialCraft",
	version = "1.2.0",
	description = "An easy-to-use plugin with all essential commands",
	authors = {"Daniel12321"})
public class CrucialCraft {

	private final PluginContainer container;
	private final Game game;
	private final Logger logger;
	private final Path path;

	private long startup_time;

//	private EconomyService economy;
	private WhitelistService whitelist;
	private BanService bans;
	private UserStorageService users;

	private PlayerDataManager playerdata;
	private Config config;
	private DataFile datafile;
	@Nullable private Kits kits;

	private TeleportManager teleports;
	private ChoiceMaps choicemaps;

	@Inject
	public CrucialCraft(final PluginContainer container, final Game game, @ConfigDir(sharedRoot = false) final Path path) {
		this.container = container;
		this.game = game;
		this.logger = LoggerFactory.getLogger("CrucialCraft");
		this.path = path;
	}

	@Listener
	public void onPreInit(@Nullable final GamePreInitializationEvent e) {
		this.logger.info("Registering Custom Data...");

		this.game.getDataManager().register(PowerToolData.class, ImmutablePowerToolData.class, new PowerToolDataBuilder());

		this.logger.info("Custom Data registered successfully.");
	}

	@Listener
	public void onInit(@Nullable final GameInitializationEvent e) {
		this.logger.info("Starting Plugin...");

		this.startup_time = System.currentTimeMillis();

		if (!Files.exists(this.path)) {
			try { Files.createDirectories(this.path); }
			catch (final IOException exc) { this.logger.error("Failed to create root plugin directory: {}", exc); }
		}

		// Game Object Initialization

		this.logger.info("Loading manager instances...");

		this.playerdata = new HoconPlayerDataManager(this, this.path.resolve("playerdata"));
		this.config = new Config(this, this.path.resolve("config.conf"));
		this.datafile = new DataFile(this, this.path.resolve("data.conf"));
		if (this.config.isKitsEnabled()) { this.kits = new Kits(this, this.path.resolve("kits.conf")); }

		this.teleports = new TeleportManager(this);
		this.choicemaps = new ChoiceMaps(this);

		// Listeners

		this.game.getEventManager().registerListeners(this, new ClientListener(this));
		this.game.getEventManager().registerListeners(this, new PlayerListener(this));
		this.game.getEventManager().registerListeners(this, new WorldListener(this));
		if (this.config.isChatEnabled()) { this.game.getEventManager().registerListeners(this, new ChatListener(this)); }
		if (this.config.isTeleportDelay()) { this.game.getEventManager().registerListeners(this, this.teleports); }

		this.logger.info("Loaded manager instances successfully");

		this.logger.info("Registering Commands...");

		// Command Trees

		this.game.getCommandManager().register(this, new CommandTree(this, new CommandExp(this)).add("set", new CommandExpSet(this)).add("add", new CommandExpAdd(this)), "exp", "xp", "experience");
		if (this.config.isHomesEnabled()) { this.game.getCommandManager().register(this, new CommandTree(this, new CommandHome(this)).add("set", new CommandSetHome(this)).add("del", new CommandDelHome(this)).add("list", new CommandHomes(this)), "home", "homes"); }
		if (this.config.isJailsEnabled()) {
			this.game.getCommandManager().register(this, new CommandTree(this, new CommandJail(this)).add("list", new CommandJails(this)).add("set", new CommandSetJail(this)).add("del", new CommandDelJail(this)).add("tp", new CommandTPJail(this)), "jail", "jails");
			this.game.getCommandManager().register(this, new CommandUnjail(this), "unjail");
		}
		if (this.config.isKitsEnabled()) { this.game.getCommandManager().register(this, new CommandTree(this, new CommandKit(this)).add("list", new CommandKits(this)).add("create", new CommandKitCreate(this)), "kit"); }
		this.game.getCommandManager().register(this, new CommandTree(this, new CommandMailSend(this)).add("read", new CommandMailRead(this)).add("clear", new CommandMailClear(this)), "mail", "email", "e-mail");
		this.game.getCommandManager().register(this, new CommandTree(this, new CommandSpawn(this)).add("set", new CommandSetSpawn(this)), "spawn");
		this.game.getCommandManager().register(this, new CommandTree(this, new CommandTPNewbieSpawn(this)).add("set", new CommandSetNewbieSpawn(this)).add("del",  new CommandDelNewbieSpawn(this)), "newbiespawn");
		if (this.config.isWarpsEnabled()) { this.game.getCommandManager().register(this, new CommandTree(this, new CommandWarp(this)).add("set", new CommandSetWarp(this)).add("del", new CommandDelWarp(this)).add("list", new CommandWarps(this)), "warp", "warps"); }

		// Loose Commands

		this.game.getCommandManager().register(this, new CommandAntioch(this), "antioch", "grenade");
		this.game.getCommandManager().register(this, new CommandBack(this), "back", "lastlocation");
//		this.game.getCommandManager().register(this, new CommandBreak(this), "break");
		this.game.getCommandManager().register(this, new CommandBroadcast(this), "broadcast", "bc", "shout");
		this.game.getCommandManager().register(this, new CommandBurn(this), "burn", "ignitr");
		this.game.getCommandManager().register(this, new CommandButcher(this), "butcher");
		this.game.getCommandManager().register(this, new CommandClearInventory(this), "ci", "clearinv", "clearinventory");
		this.game.getCommandManager().register(this, new CommandDispose(this), "dispose", "trash", "trashcan");
		this.game.getCommandManager().register(this, new CommandEnchant(this), "enchant", "enchant");
		this.game.getCommandManager().register(this, new CommandEnderchest(this), "enderchest", "echest");
		this.game.getCommandManager().register(this, new CommandFeed(this), "feed");
		this.game.getCommandManager().register(this, new CommandFireball(this), "fireball");
		this.game.getCommandManager().register(this, new CommandFly(this), "fly");
		this.game.getCommandManager().register(this, new CommandGamemode(this), "gm", "gamemode");
		this.game.getCommandManager().register(this, new CommandGive(this), "give", "giveitem");
		this.game.getCommandManager().register(this, new CommandGod(this), "god");
		this.game.getCommandManager().register(this, new CommandHat(this), "hat");
		this.game.getCommandManager().register(this, new CommandHelpop(this), "helpop", "helpoperator");
		this.game.getCommandManager().register(this, new CommandHeal(this), "heal");
		this.game.getCommandManager().register(this, new CommandInvsee(this), "invsee", "inventorysee");
		this.game.getCommandManager().register(this, new CommandItem(this), "i", "item");
		this.game.getCommandManager().register(this, new CommandItemlore(this), "itemlore", "addlore");
		this.game.getCommandManager().register(this, new CommandItemname(this), "itemname", "rename");
		this.game.getCommandManager().register(this, new CommandJump(this), " jump", "j");
		this.game.getCommandManager().register(this, new CommandKick(this), "kick");
		this.game.getCommandManager().register(this, new CommandKickall(this), "kickall");
		this.game.getCommandManager().register(this, new CommandKill(this), "kill", "murder");
		this.game.getCommandManager().register(this, new CommandKillall(this), "killall");
		this.game.getCommandManager().register(this, new CommandLag(this), "lag", "gc", "mem", "memory", "tps");
		this.game.getCommandManager().register(this, new CommandList(this), "list", "l", "players");
		this.game.getCommandManager().register(this, new CommandMe(this), "me");
		this.game.getCommandManager().register(this, new CommandMessage(this), "message", "msg", "w", "m");
		this.game.getCommandManager().register(this, new CommandMore(this), "more", "fillstack");
		this.game.getCommandManager().register(this, new CommandMOTD(this), "motd");
		this.game.getCommandManager().register(this, new CommandMute(this), "mute");
		this.game.getCommandManager().register(this, new CommandNear(this), "near", "nearby");
		this.game.getCommandManager().register(this, new CommandNick(this), "nick", "nickname");
		this.game.getCommandManager().register(this, new CommandPing(this), "ping", "pong", "latency");
		this.game.getCommandManager().register(this, new CommandPlaytime(this), "playtime", "onlinetime");
		this.game.getCommandManager().register(this, new CommandPowerTool(this), "powertool", "pt");
		this.game.getCommandManager().register(this, new CommandRealname(this), "realname");
		this.game.getCommandManager().register(this, new CommandRepair(this), "repair", "fix");
		this.game.getCommandManager().register(this, new CommandReply(this), "r", "reply");
		this.game.getCommandManager().register(this, new CommandRules(this), "rules");
		this.game.getCommandManager().register(this, new CommandSeen(this), "seen", "lastonline");
		this.game.getCommandManager().register(this, new CommandSnowball(this), "snowball");
		this.game.getCommandManager().register(this, new CommandSpawner(this), "spawner", "mobspawner");
		this.game.getCommandManager().register(this, new CommandSpawnMob(this), "spawnmob");
		this.game.getCommandManager().register(this, new CommandStop(this), "stop", "stopserver");
		this.game.getCommandManager().register(this, new CommandSpeed(this), "speed");
		this.game.getCommandManager().register(this, new CommandSudo(this), "sudo", "force");
		this.game.getCommandManager().register(this, new CommandSuicide(this), "suicide");
		this.game.getCommandManager().register(this, new CommandTime(this), "time", "settime");
		this.game.getCommandManager().register(this, new CommandTop(this), "top");
		this.game.getCommandManager().register(this, new CommandTP(this), "tp");
		this.game.getCommandManager().register(this, new CommandTPA(this),  "tpa");
		this.game.getCommandManager().register(this, new CommandTPAAll(this), "tpaall");
		this.game.getCommandManager().register(this, new CommandTPAHere(this), "tpahere");
		this.game.getCommandManager().register(this, new CommandTPAll(this),  "tpall");
		this.game.getCommandManager().register(this, new CommandTPHere(this), "tphere");
		this.game.getCommandManager().register(this, new CommandTPPos(this), "tppos");
		this.game.getCommandManager().register(this, new CommandUnbreakable(this), "unbreakable");
		this.game.getCommandManager().register(this, new CommandUnmute(this), "unmute");
		this.game.getCommandManager().register(this, new CommandVanish(this), "vanish", "v");
		this.game.getCommandManager().register(this, new CommandWeather(this), "weather");
		this.game.getCommandManager().register(this, new CommandWhois(this), "whois");
		this.game.getCommandManager().register(this, new CommandWorkbench(this), "workbench", "craft", "crafting", "craftingtable");

		this.logger.info("Registered commands succesfully.");

		this.logger.info("Plugin started succesfully in " + String.valueOf(System.currentTimeMillis() - this.startup_time) + " milliseconds.");

		/*
		 * TODO Commands:
		 * firework, skull, afk, info, socialspy,
		 * lightning, nuke, kittycannon,
		 * customtext, book
		 * 
		 * /break - unimplemented: net.minecraft.world.WorldServer#digBlock is abstract
		 * 
		 * Systems:
		 * rankup, afk, economy integration
		*/

		/*
		 * Added in v1.1.0: /jump, /seen, /kit, /createkit, /kits
		 * Added in v1.1.1: /tpa, /tpahere, /tpaall
		 * Added in v1.1.2: /mail, /dispose, /enchant, /helpop, /spawner, /antioch
		 * Added in v1.1.3: /newbiespawn tp, /itemname, /itemlore, /unbreakable, /whois, /lag, /stop
		 */
	}

	@Listener
	public void onPostInit(@Nullable final GamePostInitializationEvent e) {
		this.logger.info("Loading Services...");

//		this.economy = this.game.getServiceManager().provide(EconomyService.class).get();
		this.whitelist = this.game.getServiceManager().provide(WhitelistService.class).get();
		this.bans = this.game.getServiceManager().provide(BanService.class).get();
		this.users = this.game.getServiceManager().provide(UserStorageService.class).get();

		this.logger.info("Loaded services Successfully.");
	}

	@Listener
	public void onReload(@Nullable final GameReloadEvent e) {
		this.logger.info("Reloading...");

		this.game.getEventManager().unregisterPluginListeners(this);
		this.game.getScheduler().getScheduledTasks(this).forEach(t -> t.cancel());
		this.game.getCommandManager().getOwnedBy(this).forEach(this.game.getCommandManager()::removeMapping);

		this.onInit(null);
		this.onPostInit(null);

		this.logger.info("Reloaded successfully.");
	}

	@Listener
	public void onServiceChange(final ChangeServiceProviderEvent e) {
		Object provider = e.getNewProvider();

//		if (provider instanceof EconomyService) { this.economy = (EconomyService) provider; }
		if (provider instanceof WhitelistService) { this.whitelist = (WhitelistService) provider; }
		else if (provider instanceof BanService) { this.bans = (BanService) provider; }
		else if (provider instanceof UserStorageService) { this.users = (UserStorageService) provider; }

	}

	@Nonnull
	public PluginContainer getContainer() {
		return this.container;
	}

	@Nonnull
	public Game getGame() {
		return this.game;
	}

	@Nonnull
	public Logger getLogger() {
		return this.logger;
	}

	@Nonnull
	public Path getConfigDir() {
		return this.path;
	}

	public long getStartupTime() {
		return this.startup_time;
	}

//	@Nonnull
//	public EconomyService getEconomy() {
//		return this.economy;
//	}

	@Nonnull
	public WhitelistService getWhitelist() {
		return this.whitelist;
	}

	@Nonnull
	public UserStorageService getUserStorage() {
		return this.users;
	}

	@Nonnull
	public BanService getBans() {
		return this.bans;
	}

	@Nonnull
	public PlayerDataManager getPlayerData() {
		return this.playerdata;
	}

	@Nonnull
	public Config getConfig() {
		return this.config;
	}

	@Nonnull
	public DataFile getDataFile() {
		return this.datafile;
	}

	@Nullable
	public Kits getKits() {
		return this.kits;
	}

	@Nonnull
	public TeleportManager getTeleportManager() {
		return this.teleports;
	}

	@Nonnull
	public ChoiceMaps getChoiceMaps() {
		return this.choicemaps;
	}
}