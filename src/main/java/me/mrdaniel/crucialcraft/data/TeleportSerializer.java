//package me.mrdaniel.crucialcraft.data;
//
//import com.google.common.reflect.TypeToken;
//
//import ninja.leaping.configurate.ConfigurationNode;
//import ninja.leaping.configurate.objectmapping.ObjectMappingException;
//import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
//
//public class TeleportSerializer implements TypeSerializer<Teleport> {
//
//	@Override
//	public Teleport deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
//		if (value.getNode("world").isVirtual()
//				|| value.getNode("x").isVirtual()
//				|| value.getNode("y").isVirtual()
//				|| value.getNode("z").isVirtual()
//				|| value.getNode("pitch").isVirtual()
//				|| value.getNode("yaw").isVirtual()) { return null; }
//		return new Teleport(value.getNode("world").getString(),
//				value.getNode("x").getDouble(),
//				value.getNode("y").getDouble(),
//				value.getNode("z").getDouble(),
//				value.getNode("pitch").getDouble(),
//				value.getNode("yaw").getDouble());
//	}
//
//	@Override
//	public void serialize(TypeToken<?> type, Teleport teleport, ConfigurationNode value) throws ObjectMappingException {
//		value.getNode("world").setValue(teleport.world);
//		value.getNode("x").setValue(teleport.x);
//		value.getNode("y").setValue(teleport.y);
//		value.getNode("z").setValue(teleport.z);
//		value.getNode("pitch").setValue(teleport.pitch);
//		value.getNode("yaw").setValue(teleport.yaw);
//	}
//}