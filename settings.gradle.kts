rootProject.name = "hex-components"
setOf("dependencies", "anno", "binary", "examples", "interpreter", "util", "viewer")
        .forEach { module ->

    include(":hex-$module")
    project(":hex-$module").projectDir = file(module)
}
