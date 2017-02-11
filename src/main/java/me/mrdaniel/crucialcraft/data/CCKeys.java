package me.mrdaniel.crucialcraft.data;

import java.util.Map;
import java.util.Optional;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.ValueFactory;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.OptionalValue;
import org.spongepowered.api.data.value.mutable.Value;

import com.google.common.reflect.TypeToken;

@SuppressWarnings("serial")
public class CCKeys {

	public static final ValueFactory FACTORY = Sponge.getRegistry().getValueFactory();

	public static final Key<MapValue<String, Teleport>> HOMES = KeyFactory.makeMapKey(new TypeToken<Map<String, Teleport>>(){}, new TypeToken<MapValue<String, Teleport>>(){}, DataQuery.of("homes"), "cc:homes", "CrucialCraft Homes");
	public static final Key<OptionalValue<String>> NICK = KeyFactory.makeOptionalKey(new TypeToken<Optional<String>>(){}, new TypeToken<OptionalValue<String>>(){}, DataQuery.of("nick"), "cc:nick", "CrucialCraft Nick");
	public static final Key<OptionalValue<Teleport>> LAST_LOCATION = KeyFactory.makeOptionalKey(new TypeToken<Optional<Teleport>>(){}, new TypeToken<OptionalValue<Teleport>>(){}, DataQuery.of("last_location"), "cc:last_location", "CrucialCraft Last Location");
	public static final Key<OptionalValue<String>> LAST_MESSAGER = KeyFactory.makeOptionalKey(new TypeToken<Optional<String>>(){}, new TypeToken<OptionalValue<String>>(){}, DataQuery.of("last_messager"), "cc:last_messager", "CrucialCraft Last Messager");
	public static final Key<Value<Boolean>> JAILED = KeyFactory.makeSingleKey(TypeToken.of(Boolean.class), new TypeToken<Value<Boolean>>(){}, DataQuery.of("jailed"), "cc:jailed", "CrucialCraft Jailed");

	public static final Key<Value<String>> WORLD = KeyFactory.makeSingleKey(TypeToken.of(String.class), new TypeToken<Value<String>>(){}, DataQuery.of("world"), "cc:world", "CrucialCraft World");
	public static final Key<Value<Double>> X = KeyFactory.makeSingleKey(TypeToken.of(Double.class), new TypeToken<Value<Double>>(){}, DataQuery.of("x"), "cc:x", "CrucialCraft X");
	public static final Key<Value<Double>> Y = KeyFactory.makeSingleKey(TypeToken.of(Double.class), new TypeToken<Value<Double>>(){}, DataQuery.of("y"), "cc:y", "CrucialCraft Y");
	public static final Key<Value<Double>> Z = KeyFactory.makeSingleKey(TypeToken.of(Double.class), new TypeToken<Value<Double>>(){}, DataQuery.of("z"), "cc:z", "CrucialCraft Z");
	public static final Key<Value<Double>> PITCH = KeyFactory.makeSingleKey(TypeToken.of(Double.class), new TypeToken<Value<Double>>(){}, DataQuery.of("pitch"), "cc:x", "CrucialCraft Pitch");
	public static final Key<Value<Double>> YAW = KeyFactory.makeSingleKey(TypeToken.of(Double.class), new TypeToken<Value<Double>>(){}, DataQuery.of("yaw"), "cc:yaw", "CrucialCraft Yaw");
}