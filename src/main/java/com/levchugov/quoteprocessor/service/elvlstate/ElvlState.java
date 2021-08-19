package com.levchugov.quoteprocessor.service.elvlstate;

import com.levchugov.quoteprocessor.model.Elvl;

public interface ElvlState {

    ElvlState computeState();

    Elvl computeElvl();

}
