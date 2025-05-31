package com.github.hondams.quarkus.ap.fw.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;

class QuarkusApFwProcessor {

    private static final String FEATURE = "quarkus-ap-fw";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }
}
