package com.utnfrba.geogenius.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WidgetViewModel : ViewModel() {
    fun setBookmarkCount(context: Context, id: GlanceId) {
       viewModelScope.launch() {
           GeoGeniusWidget().update(context, id)
       }
    }
}