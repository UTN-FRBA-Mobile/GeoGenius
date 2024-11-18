package com.utnfrba.geogenius.widget

import android.content.Context
import androidx.glance.GlanceId
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class WidgetViewModel : ViewModel() {
    fun updateWidget(context: Context, id: GlanceId) {
       viewModelScope.launch() {
           GeoGeniusWidget().update(context, id)
       }
    }
}