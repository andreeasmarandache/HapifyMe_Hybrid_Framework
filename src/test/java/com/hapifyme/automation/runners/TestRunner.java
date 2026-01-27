package com.hapifyme.automation.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        // Calea către fișierele .feature
        features = "src/test/resources/features",
        // Pachetul unde sunt Step Definitions și Hooks
        glue = "com.hapifyme.automation.steps",
        // Plugin-uri pentru raportare în consolă și HTML
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        // Dacă e true, verifică doar dacă pașii sunt implementați, fără să ruleze browserul
        dryRun = false,
        // Opțional: Rulare pe bază de tag-uri
        tags = "@ui or @hybrid"
)
public class TestRunner {
    // Clasa rămâne goală, este doar un suport pentru adnotări
}
