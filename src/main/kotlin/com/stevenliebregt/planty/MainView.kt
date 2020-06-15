package com.stevenliebregt.planty

import com.stevenliebregt.planty.editor.EditorFragment
import com.stevenliebregt.planty.event.EditDocumentEvent
import com.stevenliebregt.planty.event.NewDocumentEvent
import com.stevenliebregt.planty.event.RenderEvent
import com.stevenliebregt.planty.preview.PreviewView
import javafx.scene.control.TabPane
import javafx.scene.layout.Priority
import tornadofx.*

class MainView : View() {
    private val controller: MainController by inject()

    private lateinit var tabPane: TabPane
    private lateinit var previewPane: PreviewView

    private val editorFragments = mutableListOf<EditorFragment>()

    override val root = vbox {
        menubar {
            menu(messages["File"]) {
                item(messages["New"]).action { fire(NewDocumentEvent()) }
                item(messages["Open"]).action { controller.openFileDialog() }
                item(messages["Exit"]).action { controller.quit() }
            }
        }
        splitpane {
            tabPane = tabpane {
                // Listen for events to add a new document tab
                subscribe<NewDocumentEvent> {
                    val fragment = find<EditorFragment>()
                    editorFragments += fragment

                    tab("") {
                        textProperty().bind(fragment.titleProperty)
                        add(fragment)

                        setOnCloseRequest { fragment.subscription.unsubscribe() }
                    }
                }

                // Listen for events to add a tab for existing documents
                subscribe<EditDocumentEvent> { event ->
                    val fragment = find<EditorFragment>(mapOf(EditorFragment::file to event.file))
                    editorFragments += fragment

                    tab("") {
                        textProperty().bind(fragment.titleProperty)
                        add(fragment)

                        setOnCloseRequest { fragment.subscription.unsubscribe() }
                    }
                }
            }

            previewPane = find()
            add(previewPane)

            setDividerPositions(0.5)

            vgrow = Priority.ALWAYS
        }
    }

    init {
        title = "Planty"

        // Render when the active tab changes
        tabPane.selectionModel.selectedIndexProperty().addListener { _, _, newValue ->
            val index = newValue.toInt()

            if (index < 0) {
                fire(RenderEvent(null))
                return@addListener
            }

            fire(RenderEvent(editorFragments[index].codeArea.text))
        }
    }
}