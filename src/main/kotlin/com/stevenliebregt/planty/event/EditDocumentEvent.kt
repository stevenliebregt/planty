package com.stevenliebregt.planty.event

import tornadofx.*
import java.io.File

class EditDocumentEvent(val file: File) : FXEvent()