package com.squidhq.plugin;

public class APISingleton {

    public static API _api;

    /**
     * @deprecated {@see <a href="https://github.com/masterdoctor/SquidHQ-Reef/wiki/Developers">https://github.com/masterdoctor/SquidHQ-Reef/wiki/Developers</a>}
     */
    public static API getAPI() {
        return _api;
    }

}
