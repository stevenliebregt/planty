package com.stevenliebregt.planty.editor

import javafx.scene.text.Font
import tornadofx.Stylesheet

class EditorStyles : Stylesheet() {
    init {
        text {
            font = Font.font("Fira Code Light", 12.0)
        }
    }
}