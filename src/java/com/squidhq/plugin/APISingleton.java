package com.squidhq.plugin;

import com.squidhq.reef.Reef;

public class APISingleton {

    public static API _api;

    /**
     * @deprecated Use {@link Reef#getAPI()} instead.
     */
    public static API getAPI() {
        return _api;
    }

}
