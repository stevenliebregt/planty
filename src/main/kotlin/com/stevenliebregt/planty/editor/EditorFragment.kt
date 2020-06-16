package com.stevenliebregt.planty.editor

import com.stevenliebregt.planty.event.RenderEvent
import javafx.stage.FileChooser
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.reactfx.Subscription
import tornadofx.Fragment
import tornadofx.addStylesheet
import tornadofx.get
import java.io.File
import java.io.FileWriter
import java.time.Duration

class EditorFragment : Fragment() {
    val codeArea = CodeArea()

    val initialFile: File? by param()
    var file: File? = null
        private set

    val subscription: Subscription

    override val root = VirtualizedScrollPane(codeArea)

    init {
        titleProperty.set(messages["NewDocument"])

        codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
        codeArea.addStylesheet(EditorStyles::class)
        subscription = codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe { fire(RenderEvent(codeArea.text, file)) }

        // Load the provided file
        initialFile?.let { loadFile(it) }
    }

    fun save() {
        file?.let {
            writeFile(it, codeArea.text)
            return
        }

        val fileChooser = FileChooser()
        fileChooser.initialFileName = "untitled.puml"
        val result = fileChooser.showSaveDialog(null)

        result?.let {
            file = it
            writeFile(it, codeArea.text)
        }
    }

    private fun loadFile(file: File) {
        titleProperty.set(file.name)
        codeArea.replaceText(file.readLines().joinToString(System.lineSeparator()))

        this.file = file
    }

    private fun writeFile(file: File, contents: String) {
        val fileWriter = FileWriter(file)
        fileWriter.write(contents)
        fileWriter.close()

        titleProperty.set(file.name)
    }
}