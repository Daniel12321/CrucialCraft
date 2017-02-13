package me.mrdaniel.crucialcraft.data;

import java.util.Optional;

import javax.annotation.Nonnull;

import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

public class CCPlayerDataBuilder extends AbstractDataBuilder<CCPlayerData> implements DataManipulatorBuilder<CCPlayerData, ImmutableCCPlayerData> {

    public CCPlayerDataBuilder() {
    	super(CCPlayerData.class, 1);
    }

    @Override
    public CCPlayerData create() {
        return new CCPlayerData();
    }

    @Override
    public Optional<CCPlayerData> createFrom(@Nonnull DataHolder holder) {
        return create().fill(holder);
    }

    @Override
    protected Optional<CCPlayerData> buildContent(@Nonnull DataView view) throws InvalidDataException {
    	return create().from(view);
    }
}