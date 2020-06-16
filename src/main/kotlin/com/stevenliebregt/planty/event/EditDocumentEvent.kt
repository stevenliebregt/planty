package com.stevenliebregt.planty.event

import tornadofx.FXEvent
import java.io.File

class EditDocumentEvent(val file: File) : FXEvent()