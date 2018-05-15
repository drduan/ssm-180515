package utils.log;

import ch.qos.logback.core.rolling.FixedWindowRollingPolicy;
import utils.Constant;

public class CustomFixedWindowRollingPolicy extends FixedWindowRollingPolicy {

    @Override
    protected int getMaxWindowSize() {
        return Constant.MAX_WINDOW_SIZE;
    }
}

