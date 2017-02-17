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
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
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
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.commands.exp.*;
import me.mrdaniel.crucialcraft.commands.home.*;
import me.mrdaniel.crucialcraft.commands.item.*;
import me.mrdaniel.crucialcraft.commands.jail.*;
import me.mrdaniel.crucialcraft.commands.kits.*;
import me.mrdaniel.crucialcraft.commands.message.*;
import me.mrdaniel.crucialcraft.commands.simple.*;
import me.mrdaniel.crucialcraft.commands.tp.*;
import me.mrdaniel.crucialcraft.commands.warp.*;
import me.mrdaniel.crucialcraft.data.*;
import me.mrdaniel.crucialcraft.io.*;
import me.mrdaniel.crucialcraft.listeners.*;
import me.mrdaniel.crucialcraft.teleport.TeleportManager;

import me.mrdaniel.crucialcraft.utils.ChoiceMaps;

@Plugin(id = "crucialcraft",
	name = "CrucialCraft",
	version = "1.1.2",
	description = "An easy-to-use plugin with all essential commands",
	authors = {"Mr_Daniel12321"},
	url = "https://github.com/Daniel12321/CrucialCraft/releases")
public class CrucialCraft {

	private final PluginContainer container;
	private final Game game;
	private final Logger logger;
	private final Path path;

	private WhitelistService whitelist;
	private BanService bans;
	private UserStorageService users;

	private PlayerDataManager playerdata;
	private Config config;
	private DataFile datafile;
	private Kits kits;

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

		long starttime = System.currentTimeMillis();

		if (!Files.exists(this.path)) {
			try { Files.createDirectories(this.path); }
			catch (final IOException exc) { this.logger.error("Failed to create root plugin directory: {}", exc); }
		}

		// Game Object Initialization

		this.logger.info("Loading manager instances...");

		this.playerdata = new HoconPlayerDataManager(this, this.path.resolve("playerdata"));
		this.config = new Config(this, this.path.resolve("config.conf"));
		this.datafile = new DataFile(this, this.path.resolve("data.conf"));
		this.kits = new Kits(this, this.path.resolve("kits.conf"));

		this.teleports = new TeleportManager(this);
		this.choicemaps = new ChoiceMaps(this);

		// Listeners

		this.game.getEventManager().registerListeners(this, new ClientListener(this));
		this.game.getEventManager().registerListeners(this, new PlayerListener(this));
		this.game.getEventManager().registerListeners(this, new ChatListener(this));
		this.game.getEventManager().registerListeners(this, new WorldListener(this));
		if (this.config.isTeleportDelay()) { this.game.getEventManager().registerListeners(this, this.teleports); }

		this.logger.info("Loaded manager instances successfully");

		// Permission Commands

		this.logger.info("Registering Commands...");

		CommandSpec broadcast = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Broadcast Command")).arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandBroadcast(this)).build();
		this.game.getCommandManager().register(this, broadcast, "broadcast", "bc");

		CommandSpec warps = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Warps Command")).executor(new CommandWarps(this)).build();
		this.game.getCommandManager().register(this, warps, "warps", "warplist");

		CommandSpec delwarp = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | DelWarp Command")).arguments(GenericArguments.string(Text.of("name"))).executor(new CommandDelWarp(this)).build();
		this.game.getCommandManager().register(this, delwarp, "delwarp", "deletewarp", "remwarp", "removewarp");

		CommandSpec jails = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Jails Command")).executor(new CommandJails(this)).build();
		this.game.getCommandManager().register(this, jails, "jails", "jaillist");

		CommandSpec deljail = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | DelJail Command")).arguments(GenericArguments.string(Text.of("name"))).executor(new CommandDelJail(this)).build();
		this.game.getCommandManager().register(this, deljail, "deljail", "deletejail", "remjail", "removejail");

		CommandSpec motd = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | MOTD Command")).executor(new CommandMOTD(this)).build();
		this.game.getCommandManager().register(this, motd, "motd");

		CommandSpec rules = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Rules Command")).executor(new CommandRules(this)).build();
		this.game.getCommandManager().register(this, rules, "rules");

		CommandSpec time = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Time Command")).arguments(GenericArguments.string(Text.of("time")), GenericArguments.optionalWeak(GenericArguments.string(Text.of("world")))).executor(new CommandTime(this)).build();
		this.game.getCommandManager().register(this, time, "time", "settime");

		CommandSpec weather = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Weather Command")).arguments(GenericArguments.string(Text.of("weather")), GenericArguments.optionalWeak(GenericArguments.longNum(Text.of("duration"))), GenericArguments.optionalWeak(GenericArguments.string(Text.of("world")))).executor(new CommandWeather(this)).build();
		this.game.getCommandManager().register(this, weather, "weather", "setweather");

		CommandSpec delnewbiespawn = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | DelNewbieSpawn Command")).executor(new CommandDelNewbieSpawn(this)).build();
		this.game.getCommandManager().register(this, delnewbiespawn, "delnewbiespawn");

		CommandSpec realname = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | RealName Command")).arguments(GenericArguments.string(Text.of("nick"))).executor(new CommandRealname(this)).build();
		this.game.getCommandManager().register(this, realname, "realname");

		CommandSpec list = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | List Command")).executor(new CommandList(this)).build();
		this.game.getCommandManager().register(this, list, "list", "l", "playerlist");

		CommandSpec me = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Me Command")).arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandMe(this)).build();
		this.game.getCommandManager().register(this, me, "me");

		CommandSpec kickall = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Kickall Command")).arguments(GenericArguments.remainingRawJoinedStrings(Text.of("reason"))).executor(new CommandKickall(this)).build();
		this.game.getCommandManager().register(this, kickall, "kickall");

		CommandSpec butcher = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Butcher Command")).arguments(GenericArguments.optionalWeak(GenericArguments.string(Text.of("world")))).executor(new CommandButcher(this)).build();
		this.game.getCommandManager().register(this, butcher, "butcher", "killmonsters");

		CommandSpec killall = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Killall Command")).arguments(GenericArguments.choices(Text.of("type"), this.choicemaps.getEnityTypes()), GenericArguments.optionalWeak(GenericArguments.string(Text.of("world")))).executor(new CommandKillall(this)).build();
		this.game.getCommandManager().register(this, killall, "killall");

		CommandSpec playtime = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Playtime Command")).arguments(GenericArguments.optionalWeak(GenericArguments.userOrSource(Text.of("target")))).executor(new CommandPlaytime(this)).build();
		this.game.getCommandManager().register(this, playtime, "playtime", "onlinetime");

		CommandSpec seen = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Seen Command")).arguments(GenericArguments.userOrSource(Text.of("target"))).executor(new CommandSeen(this)).build();
		this.game.getCommandManager().register(this, seen, "seen", "lastseen");

		CommandSpec kits = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Kits Command")).executor(new CommandKits(this)).build();
		this.game.getCommandManager().register(this, kits, "kits", "kitlist");


		// Player Commands

		CommandSpec near = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Near Command")).executor(new CommandNear(this)).build();
		this.game.getCommandManager().register(this, near, "near");

		CommandSpec ping = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Ping Command")).executor(new CommandPing(this)).build();
		this.game.getCommandManager().register(this, ping, "ping", "latency");

		CommandSpec setspawn = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | SetSpawn Command")).executor(new CommandSetSpawn(this)).build();
		this.game.getCommandManager().register(this, setspawn, "setspawn");

		CommandSpec setwarp = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | SetWarp Command")).arguments(GenericArguments.string(Text.of("name"))).executor(new CommandSetWarp(this)).build();
		this.game.getCommandManager().register(this, setwarp, "setwarp", "createwarp");

		CommandSpec setjail = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | SetJail Command")).arguments(GenericArguments.string(Text.of("name"))).executor(new CommandSetJail(this)).build();
		this.game.getCommandManager().register(this, setjail, "setjail", "createjail");

		CommandSpec sethome = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | SetHome Command")).arguments(GenericArguments.string(Text.of("name"))).executor(new CommandSetHome(this)).build();
		this.game.getCommandManager().register(this, sethome, "sethome");

		CommandSpec delhome = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | DelHome Command")).arguments(GenericArguments.string(Text.of("name"))).executor(new CommandDelHome(this)).build();
		this.game.getCommandManager().register(this, delhome, "delhome", "deletehome", "remhome", "removehome");

		CommandSpec suicide = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Suicide Command")).executor(new CommandSuicide(this)).build();
		this.game.getCommandManager().register(this, suicide, "suicide");

		CommandSpec item = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Item Command")).arguments(GenericArguments.choices(Text.of("type"), this.choicemaps.getItemTypes()), GenericArguments.optionalWeak(GenericArguments.integer(Text.of("amount")))).executor(new CommandItem(this)).build();
		this.game.getCommandManager().register(this, item, "item", "i");

		CommandSpec setnewbiespawn = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | SetNewbieSpawn Command")).executor(new CommandSetNewbieSpawn(this)).build();
		this.game.getCommandManager().register(this, setnewbiespawn, "setnewbiespawn");

		CommandSpec reply = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Reply Command")).arguments(GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandReply(this)).build();
		this.game.getCommandManager().register(this, reply, "reply", "r");

		CommandSpec jump = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Jump Command")).executor(new CommandJump(this)).build();
		this.game.getCommandManager().register(this, jump, "jump", "j");

		CommandSpec tpall = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPAll Command")).executor(new CommandTPAll(this)).build();
		this.game.getCommandManager().register(this, tpall, "tpall");

		CommandSpec fireball = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Fireball Command")).executor(new CommandFireball(this)).build();
		this.game.getCommandManager().register(this, fireball, "fireball");

		CommandSpec snowball = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Snowball Command")).executor(new CommandSnowball(this)).build();
		this.game.getCommandManager().register(this, snowball, "snowball");

		CommandSpec repair = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Repair Command")).executor(new CommandRepair(this)).build();
		this.game.getCommandManager().register(this, repair, "repair", "fix");

		CommandSpec more = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | More Command")).executor(new CommandMore(this)).build();
		this.game.getCommandManager().register(this, more, "more", "fillstack");

		CommandSpec powertool = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Powertool Command")).arguments(GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandPowerTool(this)).build();
		this.game.getCommandManager().register(this, powertool, "powertool", "pt");

		CommandSpec createkit = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Create Kit Command")).arguments(GenericArguments.string(Text.of("name")), GenericArguments.integer(Text.of("time-seconds")), GenericArguments.bool(Text.of("playtime"))).executor(new CommandKitCreate(this)).build();
		this.game.getCommandManager().register(this, createkit, "createkit");

		CommandSpec kit = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Powertool Command")).arguments(GenericArguments.optionalWeak(GenericArguments.string(Text.of("name")))).executor(new CommandKit(this)).build();
		this.game.getCommandManager().register(this, kit, "kit");

		CommandSpec home = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Home Command")).arguments(GenericArguments.optionalWeak(GenericArguments.string(Text.of("name")))).executor(new CommandHome(this)).build();
		this.game.getCommandManager().register(this, home, "home", "tphome");

		CommandSpec top = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Top Command")).executor(new CommandTop(this)).build();
		this.game.getCommandManager().register(this, top, "top", "tptop");

		CommandSpec tpaall = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPAAll Command")).executor(new CommandTPAAll(this)).build();
		this.game.getCommandManager().register(this, tpaall, "tpaall");


		// Target Player Commands

		CommandSpec expadd = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Add Exp Command")).arguments(GenericArguments.integer(Text.of("exp")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandExpAdd(this)).build();
		CommandSpec expset = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Set Exp Command")).arguments(GenericArguments.integer(Text.of("exp")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandExpSet(this)).build();
		CommandSpec exp = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Exp Command"))
				.child(expset, "set")
				.child(expadd, "add")
				.build();
		this.game.getCommandManager().register(this, exp, "xp", "exp", "experience");

		CommandSpec feed = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Feed Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandFeed(this)).build();
		this.game.getCommandManager().register(this, feed, "feed");

		CommandSpec heal = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Heal Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandHeal(this)).build();
		this.game.getCommandManager().register(this, heal, "heal");

		CommandSpec fly = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Fly Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandFly(this)).build();
		this.game.getCommandManager().register(this, fly, "fly");

		CommandSpec speed = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Speed Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other"))), GenericArguments.doubleNum(Text.of("speed"))).executor(new CommandSpeed(this)).build();
		this.game.getCommandManager().register(this, speed, "speed");

		CommandSpec ci = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | ClearInventory Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandClearInventory(this)).build();
		this.game.getCommandManager().register(this, ci, "ci", "clearinv", "clearinventory");

		CommandSpec god = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | God Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandGod(this)).build();
		this.game.getCommandManager().register(this, god, "god");

		CommandSpec workbench = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Workbench Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandWorkbench(this)).build();
		this.game.getCommandManager().register(this, workbench, "workbench", "craft", "craftingtable", "crafting");

		CommandSpec vanish = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Vanish Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandVanish(this)).build();
		this.game.getCommandManager().register(this, vanish, "vanish", "v");

		CommandSpec spawn = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Spawn Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandSpawn(this)).build();
		this.game.getCommandManager().register(this, spawn, "spawn", "tpspawn");

		CommandSpec warp = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Warp Command")).arguments(GenericArguments.optionalWeak(GenericArguments.string(Text.of("name"))), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandWarp(this)).build();
		this.game.getCommandManager().register(this, warp, "warp");

		CommandSpec tpjail = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPJail Command")).arguments(GenericArguments.string(Text.of("name")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandTPJail(this)).build();
		this.game.getCommandManager().register(this, tpjail, "tpjail");

		CommandSpec tppos = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPPos Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other"))), GenericArguments.optionalWeak(GenericArguments.string(Text.of("world"))), GenericArguments.doubleNum(Text.of("x")), GenericArguments.doubleNum(Text.of("y")), GenericArguments.doubleNum(Text.of("z"))).executor(new CommandTPPos(this)).build();
		this.game.getCommandManager().register(this, tppos, "tppos");

		CommandSpec back = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Back Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandBack(this)).build();
		this.game.getCommandManager().register(this, back, "back", "lastlocation");

		CommandSpec homes = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Homes Command")).executor(new CommandHomes(this)).build();
		this.game.getCommandManager().register(this, homes, "homes", "homelist");

		CommandSpec spawnmob = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | SpawnMob Command")).arguments(GenericArguments.choices(Text.of("type"), this.choicemaps.getEnityTypes()), GenericArguments.optionalWeak(GenericArguments.integer(Text.of("amount")))).executor(new CommandSpawnMob(this)).build();
		this.game.getCommandManager().register(this, spawnmob, "spawnmob", "spawnmobs");

		CommandSpec nick = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Nick Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other"))), GenericArguments.string(Text.of("nick"))).executor(new CommandNick(this)).build();
		this.game.getCommandManager().register(this, nick, "nick", "nickname");

		CommandSpec gamemode = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Gamemode Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other"))), GenericArguments.string(Text.of("gamemode"))).executor(new CommandGamemode(this)).build();
		this.game.getCommandManager().register(this, gamemode, "gamemode", "gm");

		CommandSpec hat = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Hat Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandHat(this)).build();
		this.game.getCommandManager().register(this, hat, "hat", "helmet");

//		CommandSpec breakk = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Break Command")).executor(new CommandBreak(this)).build();
//		this.game.getCommandManager().register(this, breakk, "break");

		CommandSpec sudo = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Sudo Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.remainingRawJoinedStrings(Text.of("command"))).executor(new CommandSudo(this)).build();
		this.game.getCommandManager().register(this, sudo, "sudo", "force");

		CommandSpec jail = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Jail Command")).arguments(GenericArguments.userOrSource(Text.of("target")), GenericArguments.string(Text.of("name"))).executor(new CommandJail(this)).build();
		this.game.getCommandManager().register(this, jail, "jail");

		CommandSpec unjail = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Unjail Command")).arguments(GenericArguments.userOrSource(Text.of("target"))).executor(new CommandUnjail(this)).build();
		this.game.getCommandManager().register(this, unjail, "unjail");

		CommandSpec tp = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TP Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandTP(this)).build();
		this.game.getCommandManager().register(this, tp, "tp");

		CommandSpec msg = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Message Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.remainingRawJoinedStrings(Text.of("message"))).executor(new CommandMessage(this)).build();
		this.game.getCommandManager().register(this, msg, "msg", "whisper", "m", "w", "tell");

		CommandSpec burn = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Burn Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.optionalWeak(GenericArguments.integer(Text.of("seconds")))).executor(new CommandBurn(this)).build();
		this.game.getCommandManager().register(this, burn, "burn", "ignite");

		CommandSpec kill = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Kill Command")).arguments(GenericArguments.player(Text.of("target"))).executor(new CommandKill(this)).build();
		this.game.getCommandManager().register(this, kill, "kill", "murder");

		CommandSpec give = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Give Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.choices(Text.of("type"), this.choicemaps.getItemTypes()), GenericArguments.optional(GenericArguments.integer(Text.of("amount")))).executor(new CommandGive(this)).build();
		this.game.getCommandManager().register(this, give, "give");

		CommandSpec kick = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Kick Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.remainingRawJoinedStrings(Text.of("reason"))).executor(new CommandKick(this)).build();
		this.game.getCommandManager().register(this, kick, "kick");

		CommandSpec mute = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Mute Command")).arguments(GenericArguments.userOrSource(Text.of("target"))).executor(new CommandMute(this)).build();
		this.game.getCommandManager().register(this, mute, "mute", "silence");

		CommandSpec unmute = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Unmute Command")).arguments(GenericArguments.userOrSource(Text.of("target"))).executor(new CommandUnmute(this)).build();
		this.game.getCommandManager().register(this, unmute, "unmute", "unsilence");


		// Player Target Player Commands

		CommandSpec enderchest = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Enderchest Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("target")))).executor(new CommandEnderchest(this)).build();
		this.game.getCommandManager().register(this, enderchest, "enderchest");

		CommandSpec invsee = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Invsee Command")).arguments(GenericArguments.player(Text.of("target"))).executor(new CommandInvsee(this)).build();
		this.game.getCommandManager().register(this, invsee, "invsee", "inventorysee", "seeinv", "seeinventory");

		CommandSpec tphere = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPHere Command")).arguments(GenericArguments.player(Text.of("target"))).executor(new CommandTPHere(this)).build();
		this.game.getCommandManager().register(this, tphere, "tphere");

		CommandSpec tpa = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPA Command")).arguments(GenericArguments.player(Text.of("target"))).executor(new CommandTPA(this)).build();
		this.game.getCommandManager().register(this, tpa, "tpa");

		CommandSpec tpahere = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPAHere Command")).arguments(GenericArguments.player(Text.of("target"))).executor(new CommandTPAHere(this)).build();
		this.game.getCommandManager().register(this, tpahere, "tpahere");


		this.logger.info("Registered commands succesfully.");

		this.logger.info("Plugin started succesfully in " + String.valueOf(System.currentTimeMillis() - starttime) + " milliseconds.");

		/*
		 * Commands: 
		 * enchant, firework, skull, afk, info, socialspy,
		 * mail, spawner, antioch, gc, lightning, nuke,
		 * customtext, tree, bigtree, book,
		 * 
		 * /break unimplemented: net.minecraft.world.WorldServer#digBlock is abstract
		 * 
		 * Systems:
		 * rankup, afk
		*/

		/*
		 * Added in v1.1.0: /jump, /seen, /kit, /createkit, /kits
		 * Added in v1.1.1: /tpa, /tpahere, /tpaall
		 * Added in v1.1.2: /mail
		 */
	}

	@Listener
	public void onPostInit(@Nullable final GamePostInitializationEvent e) {
		this.logger.info("Loading Services...");

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

	@Nonnull
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