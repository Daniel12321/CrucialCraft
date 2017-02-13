package me.mrdaniel.crucialcraft.data;

import java.util.Optional;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class PowerToolDataBuilder extends AbstractDataBuilder<PowerToolData> implements DataManipulatorBuilder<PowerToolData, ImmutablePowerToolData> {

	public PowerToolDataBuilder() {
		super(PowerToolData.class, 1);
	}

	@Override
	public PowerToolData create() {
		return new PowerToolData("");
	}

	@Override
	public Optional<PowerToolData> createFrom(DataHolder holder) {
		return this.create().fill(holder);
	}

	@Override
	protected Optional<PowerToolData> buildContent(DataView view) throws InvalidDataException {
		return this.create().from(view);
	}
}