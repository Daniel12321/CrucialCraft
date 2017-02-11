package me.mrdaniel.crucialcraft;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

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
import org.spongepowered.api.service.whitelist.WhitelistService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import me.mrdaniel.crucialcraft.commands.exp.CommandExpAdd;
import me.mrdaniel.crucialcraft.commands.exp.CommandExpSet;
import me.mrdaniel.crucialcraft.commands.home.CommandDelHome;
import me.mrdaniel.crucialcraft.commands.home.CommandHome;
import me.mrdaniel.crucialcraft.commands.home.CommandHomes;
import me.mrdaniel.crucialcraft.commands.home.CommandSetHome;
import me.mrdaniel.crucialcraft.commands.item.CommandGive;
import me.mrdaniel.crucialcraft.commands.item.CommandItem;
import me.mrdaniel.crucialcraft.commands.jail.CommandDelJail;
import me.mrdaniel.crucialcraft.commands.jail.CommandJail;
import me.mrdaniel.crucialcraft.commands.jail.CommandJails;
import me.mrdaniel.crucialcraft.commands.jail.CommandSetJail;
import me.mrdaniel.crucialcraft.commands.jail.CommandTPJail;
import me.mrdaniel.crucialcraft.commands.jail.CommandUnjail;
import me.mrdaniel.crucialcraft.commands.message.CommandMessage;
import me.mrdaniel.crucialcraft.commands.message.CommandReply;
import me.mrdaniel.crucialcraft.commands.simple.CommandBroadcast;
import me.mrdaniel.crucialcraft.commands.simple.CommandBurn;
import me.mrdaniel.crucialcraft.commands.simple.CommandClearInventory;
import me.mrdaniel.crucialcraft.commands.simple.CommandEnderchest;
import me.mrdaniel.crucialcraft.commands.simple.CommandFeed;
import me.mrdaniel.crucialcraft.commands.simple.CommandFireball;
import me.mrdaniel.crucialcraft.commands.simple.CommandFly;
import me.mrdaniel.crucialcraft.commands.simple.CommandGod;
import me.mrdaniel.crucialcraft.commands.simple.CommandHat;
import me.mrdaniel.crucialcraft.commands.simple.CommandHeal;
import me.mrdaniel.crucialcraft.commands.simple.CommandInvsee;
import me.mrdaniel.crucialcraft.commands.simple.CommandKill;
import me.mrdaniel.crucialcraft.commands.simple.CommandMOTD;
import me.mrdaniel.crucialcraft.commands.simple.CommandMore;
import me.mrdaniel.crucialcraft.commands.simple.CommandNick;
import me.mrdaniel.crucialcraft.commands.simple.CommandPing;
import me.mrdaniel.crucialcraft.commands.simple.CommandRepair;
import me.mrdaniel.crucialcraft.commands.simple.CommandRules;
import me.mrdaniel.crucialcraft.commands.simple.CommandSetSpawn;
import me.mrdaniel.crucialcraft.commands.simple.CommandSnowball;
import me.mrdaniel.crucialcraft.commands.simple.CommandSpawn;
import me.mrdaniel.crucialcraft.commands.simple.CommandSpawnMob;
import me.mrdaniel.crucialcraft.commands.simple.CommandSpeed;
import me.mrdaniel.crucialcraft.commands.simple.CommandSudo;
import me.mrdaniel.crucialcraft.commands.simple.CommandSuicide;
import me.mrdaniel.crucialcraft.commands.simple.CommandVanish;
import me.mrdaniel.crucialcraft.commands.simple.CommandWorkbench;
import me.mrdaniel.crucialcraft.commands.tp.CommandBack;
import me.mrdaniel.crucialcraft.commands.tp.CommandTP;
import me.mrdaniel.crucialcraft.commands.tp.CommandTPHere;
import me.mrdaniel.crucialcraft.commands.tp.CommandTPPos;
import me.mrdaniel.crucialcraft.commands.warp.CommandDelWarp;
import me.mrdaniel.crucialcraft.commands.warp.CommandSetWarp;
import me.mrdaniel.crucialcraft.commands.warp.CommandWarp;
import me.mrdaniel.crucialcraft.commands.warp.CommandWarps;
import me.mrdaniel.crucialcraft.data.CCPlayerData;
import me.mrdaniel.crucialcraft.data.CCPlayerDataBuilder;
import me.mrdaniel.crucialcraft.data.ImmutableCCPlayerData;
import me.mrdaniel.crucialcraft.data.Teleport;
import me.mrdaniel.crucialcraft.data.TeleportTranslator;
import me.mrdaniel.crucialcraft.io.Config;
import me.mrdaniel.crucialcraft.io.DataFile;
import me.mrdaniel.crucialcraft.io.Kits;
import me.mrdaniel.crucialcraft.listeners.ClientListener;
import me.mrdaniel.crucialcraft.listeners.PlayerListener;
import me.mrdaniel.crucialcraft.utils.ChoiceMaps;

@Singleton
@Plugin(id = "crucialcraft", name = "CrucialCraft", version = "1.0", description = "An easy-to-use plugin with all essential commands", authors = {"Mr_Daniel12321"})
public class CrucialCraft {

	private final PluginContainer container;
	private final Game game;
	private final Logger logger;
	private final Path path;

	private WhitelistService whitelist;
	private BanService bans;

	private Config config;
	private DataFile datafile;
	private Kits kits;

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

		this.game.getDataManager().registerTranslator(Teleport.class, new TeleportTranslator());
		this.game.getDataManager().register(CCPlayerData.class, ImmutableCCPlayerData.class, new CCPlayerDataBuilder(this));

		this.logger.info("Custom Data registered successfully.");
	}
	@Listener
	public void onInit(@Nullable final GameInitializationEvent e) {
		this.logger.info("Starting Plugin...");

		if (!Files.exists(this.path)) {
			try { Files.createDirectories(this.path); }
			catch (final IOException exc) { this.logger.error("Failed to create root plugin directory: {}", exc); }
		}
		this.config = new Config(this, this.path.resolve("config.conf"));
		this.datafile = new DataFile(this, this.path.resolve("data.conf"));
		this.kits = new Kits(this, this.path.resolve("kits.conf"));
		this.choicemaps = new ChoiceMaps(this);

		this.game.getEventManager().registerListeners(this, new ClientListener(this));
		this.game.getEventManager().registerListeners(this, new PlayerListener(this));

		// All Sources Commands
		CommandSpec broadcast = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Broadcast Command")).arguments(GenericArguments.remainingJoinedStrings(Text.of("message"))).executor(new CommandBroadcast(this)).build();
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



		// Player (or Player Specified) Commands
		CommandSpec expadd = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Add Exp Command")).arguments(GenericArguments.integer(Text.of("exp")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandExpAdd(this)).build();
		CommandSpec expset = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Set Exp Command")).arguments(GenericArguments.integer(Text.of("exp")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandExpSet(this)).build();
		CommandSpec exp = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Exp Command"))
				.child(expset, "set")
				.child(expadd, "add")
				.build();
		this.game.getCommandManager().register(this, exp, "exp", "experience");

		CommandSpec feed = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Feed Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandFeed(this)).build();
		this.game.getCommandManager().register(this, feed, "feed");

		CommandSpec heal = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Heal Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandHeal(this)).build();
		this.game.getCommandManager().register(this, heal, "heal");

		CommandSpec fly = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Fly Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandFly(this)).build();
		this.game.getCommandManager().register(this, fly, "fly");

		CommandSpec speed = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Speed Command")).arguments(GenericArguments.doubleNum(Text.of("speed")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandSpeed(this)).build();
		this.game.getCommandManager().register(this, speed, "speed");

		CommandSpec ci = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | ClearInventory Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandClearInventory(this)).build();
		this.game.getCommandManager().register(this, ci, "ci", "clearinv", "clearinventory");

		CommandSpec god = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | God Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandGod(this)).build();
		this.game.getCommandManager().register(this, god, "god");

		CommandSpec fireball = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Fireball Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandFireball(this)).build();
		this.game.getCommandManager().register(this, fireball, "fireball");

		CommandSpec snowball = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Snowball Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandSnowball(this)).build();
		this.game.getCommandManager().register(this, snowball, "snowball");

		CommandSpec workbench = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Workbench Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandWorkbench(this)).build();
		this.game.getCommandManager().register(this, workbench, "workbench", "craft", "craftingtable", "crafting");

		CommandSpec vanish = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Vanish Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandVanish(this)).build();
		this.game.getCommandManager().register(this, vanish, "vanish");

		CommandSpec spawn = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Spawn Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandSpawn(this)).build();
		this.game.getCommandManager().register(this, spawn, "spawn");

		CommandSpec warp = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Warp Command")).arguments(GenericArguments.optionalWeak(GenericArguments.string(Text.of("name"))), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandWarp(this)).build();
		this.game.getCommandManager().register(this, warp, "warp");

		CommandSpec tpjail = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPJail Command")).arguments(GenericArguments.string(Text.of("name")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandTPJail(this)).build();
		this.game.getCommandManager().register(this, tpjail, "tpjail");

		CommandSpec tppos = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPPos Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other"))), GenericArguments.optionalWeak(GenericArguments.world(Text.of("world"))), GenericArguments.doubleNum(Text.of("x")), GenericArguments.doubleNum(Text.of("y")), GenericArguments.doubleNum(Text.of("z"))).executor(new CommandTPPos(this)).build();
		this.game.getCommandManager().register(this, tppos, "tppos");

		CommandSpec back = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Back Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandBack(this)).build();
		this.game.getCommandManager().register(this, back, "back");

		CommandSpec homes = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Homes Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandHomes(this)).build();
		this.game.getCommandManager().register(this, homes, "homes", "homelist");

		CommandSpec home = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Home Command")).arguments(GenericArguments.optionalWeak(GenericArguments.string(Text.of("name")))).executor(new CommandHome(this)).build();
		this.game.getCommandManager().register(this, home, "home", "tphome");

		CommandSpec spawnmob = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | SpawnMob Command")).arguments(GenericArguments.choices(Text.of("type"), this.choicemaps.getEnityTypes()), GenericArguments.optionalWeak(GenericArguments.integer(Text.of("amount")))).executor(new CommandSpawnMob(this)).build();
		this.game.getCommandManager().register(this, spawnmob, "spawnmob");

		CommandSpec nick = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Nick Command")).arguments(GenericArguments.string(Text.of("nick")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandNick(this)).build();
		this.game.getCommandManager().register(this, nick, "nick");







		// Player Source Commands
		CommandSpec repair = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Repair Command")).executor(new CommandRepair(this)).build();
		this.game.getCommandManager().register(this, repair, "repair", "fix");

		CommandSpec hat = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Hat Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandHat(this)).build();
		this.game.getCommandManager().register(this, hat, "hat");

		CommandSpec more = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | More Command")).executor(new CommandMore(this)).build();
		this.game.getCommandManager().register(this, more, "more");

		CommandSpec ping = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Ping Command")).executor(new CommandPing(this)).build();
		this.game.getCommandManager().register(this, ping, "ping");

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




		// TargetPlayer Commands
		CommandSpec sudo = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Sudo Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.remainingJoinedStrings(Text.of("command"))).executor(new CommandSudo(this)).build();
		this.game.getCommandManager().register(this, sudo, "sudo");

		CommandSpec jail = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Jail Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.string(Text.of("name"))).executor(new CommandJail(this)).build();
		this.game.getCommandManager().register(this, jail, "jail");

		CommandSpec unjail = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Unjail Command")).arguments(GenericArguments.player(Text.of("target"))).executor(new CommandUnjail(this)).build();
		this.game.getCommandManager().register(this, unjail, "unjail");

		CommandSpec tp = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TP Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.optionalWeak(GenericArguments.player(Text.of("other")))).executor(new CommandTP(this)).build();
		this.game.getCommandManager().register(this, tp, "tp");

		CommandSpec msg = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Message Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.remainingJoinedStrings(Text.of("message"))).executor(new CommandMessage(this)).build();
		this.game.getCommandManager().register(this, msg, "msg", "whisper", "m", "w", "tell");

		CommandSpec reply = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Reply Command")).arguments(GenericArguments.remainingJoinedStrings(Text.of("message"))).executor(new CommandReply(this)).build();
		this.game.getCommandManager().register(this, reply, "reply", "r");

		CommandSpec burn = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Burn Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.optionalWeak(GenericArguments.integer(Text.of("seconds")))).executor(new CommandBurn(this)).build();
		this.game.getCommandManager().register(this, burn, "burn");

		CommandSpec kill = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Kill Command")).arguments(GenericArguments.player(Text.of("target"))).executor(new CommandKill(this)).build();
		this.game.getCommandManager().register(this, kill, "kill");

		CommandSpec give = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Give Command")).arguments(GenericArguments.player(Text.of("target")), GenericArguments.choices(Text.of("type"), this.choicemaps.getItemTypes()), GenericArguments.optional(GenericArguments.integer(Text.of("amount")))).executor(new CommandGive(this)).build();
		this.game.getCommandManager().register(this, give, "give");





		// PlayerTargetPlayer Commands
		CommandSpec enderchest = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Enderchest Command")).arguments(GenericArguments.optionalWeak(GenericArguments.player(Text.of("target")))).executor(new CommandEnderchest(this)).build();
		this.game.getCommandManager().register(this, enderchest, "enderchest");

		CommandSpec invsee = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | Invsee Command")).arguments(GenericArguments.player(Text.of("target"))).executor(new CommandInvsee(this)).build();
		this.game.getCommandManager().register(this, invsee, "invsee");

		CommandSpec tphere = CommandSpec.builder().description(Text.of(TextColors.AQUA, "CrucialCraft | TPHere Command")).arguments(GenericArguments.player(Text.of("target"))).executor(new CommandTPHere(this)).build();
		this.game.getCommandManager().register(this, tphere, "tphere");





		this.logger.info("Plugin Started succesfull.");

		/*
		 * Commands: 
		 * enchant, firework, kit, skull, time, afk, info, list,
		 * mail, me, powertool, realname, seen, spawner, antioch,
		 * gc, kickall, kill, lightning, mute, nuke,
		 * remove/butcher, socialspy, spawnmob, weather,
		 * jump, top, tpa, tpall, tpaall, tpaccept, tpahere, tphere,
		 * break, tree, bigtree, book, customtext
		 * 
		 * Systems:
		 * time, rankup, afk, chat
		*/
	}

	@Listener
	public void onPostInit(@Nullable final GamePostInitializationEvent e) {
		this.logger.info("Getting Services...");

		this.whitelist = this.game.getServiceManager().provide(WhitelistService.class).get();
		this.bans = this.game.getServiceManager().provide(BanService.class).get();

		this.logger.info("Got Services Successfully.");
	}

	@Listener
	public void onReload(@Nullable final GameReloadEvent e) {
		this.logger.info("Reloading...");

		this.game.getEventManager().unregisterPluginListeners(this);
		this.game.getScheduler().getScheduledTasks(this).forEach(t -> t.cancel());
		this.game.getCommandManager().getOwnedBy(this).forEach(this.game.getCommandManager()::removeMapping);

		this.onInit(null);
		this.onPostInit(null);
	}

	@Listener
	public void onServiceChange(final ChangeServiceProviderEvent e) {
		if (e.getNewProvider() instanceof WhitelistService) { this.whitelist = (WhitelistService) e.getNewProvider(); }
		else if (e.getNewProvider() instanceof BanService) { this.bans = (BanService) e.getNewProvider(); }
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
	public BanService getBans() {
		return this.bans;
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
	public ChoiceMaps getChoiceMaps() {
		return this.choicemaps;
	}
}