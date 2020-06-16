package com.stevenliebregt.planty.editor

import com.stevenliebregt.planty.event.RenderEvent
import org.fxmisc.flowless.VirtualizedScrollPane
import org.fxmisc.richtext.CodeArea
import org.fxmisc.richtext.LineNumberFactory
import org.reactfx.Subscription
import tornadofx.*
import java.io.File
import java.io.FileWriter
import java.time.Duration

class EditorFragment : Fragment() {
    val codeArea = CodeArea()

    val file: File? by param()
    val subscription: Subscription

    override val root = VirtualizedScrollPane(codeArea)

    init {
        titleProperty.set(messages["NewDocument"])

        codeArea.paragraphGraphicFactory = LineNumberFactory.get(codeArea)
        subscription = codeArea.multiPlainChanges()
                .successionEnds(Duration.ofMillis(500))
                .subscribe { fire(RenderEvent(codeArea.text)) }

        // Load the provided file
        file?.let { loadFile(it) }
    }

    fun save() {
        file?.let {
            val fileWriter = FileWriter(it)
            fileWriter.write(codeArea.text)
            fileWriter.close()

            // TODO: Update checksum

            return
        }

        println("Save new")
        // TODO: Save
        // TODO: Update checksum
    }

    private fun loadFile(file: File) {
        titleProperty.set(file.name)
        codeArea.replaceText(file.readLines().joinToString(System.lineSeparator()))
        // TODO: Set checksum
    }
}