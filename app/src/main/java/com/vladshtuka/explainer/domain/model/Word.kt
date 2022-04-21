package com.vladshtuka.explainer.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Word(
    val word: String,
    val isAnswerTrue: Boolean
) : Parcelable
