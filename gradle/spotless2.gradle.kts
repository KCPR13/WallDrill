spotless {
    // version, editorConfigPath, editorConfigOverride and customRuleSets are all optional
    ktlint("1.0.0")
        .setEditorConfigPath("$projectDir/config/.editorconfig")  // sample unusual placement
        .editorConfigOverride(
            mapOf(
                "indent_size" to 2,
                // intellij_idea is the default style we preset in Spotless, you can override it referring to https://pinterest.github.io/ktlint/latest/rules/code-styles.
                "ktlint_code_style" to "intellij_idea",
            )
        )
        .customRuleSets(
            listOf(
                "io.nlopez.compose.rules:ktlint:0.4.16"
            )
        )
}