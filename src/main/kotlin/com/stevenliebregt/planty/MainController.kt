package com.stevenliebregt.planty

import com.stevenliebregt.planty.event.EditDocumentEvent
import javafx.application.Platform
import javafx.stage.FileChooser
import tornadofx.*
import kotlin.system.exitProcess

class MainController : Controller() {
    fun openFileDialog() {
        val fileChooser = FileChooser()
        val result = fileChooser.showOpenDialog(null)

        result?.let {
            fire(EditDocumentEvent(it))
        }
    }

    fun quit() {
        Platform.exit()
        exitProcess(0)
    }
}