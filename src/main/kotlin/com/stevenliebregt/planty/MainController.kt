package com.stevenliebregt.planty

import com.stevenliebregt.planty.event.EditDocumentEvent
import javafx.application.Platform
import javafx.stage.FileChooser
import net.sourceforge.plantuml.FileFormat
import tornadofx.*
import kotlin.system.exitProcess

class MainController : Controller() {
    private val exporter = Exporter()

    fun openFileDialog() {
        val fileChooser = FileChooser()
        val result = fileChooser.showOpenDialog(null)

        result?.let {
            fire(EditDocumentEvent(it))
        }
    }

    fun export(format: FileFormat, content: String) {
        val fileChooser = FileChooser()
        val result = fileChooser.showSaveDialog(null)

        result?.let { exporter.export(format, content, it) }
    }

    fun quit() {
        Platform.exit()
        exitProcess(0)
    }
}