package com.utnfrba.geogenius.screens.filters

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FiltersRepository {
    val _filters = MutableStateFlow<List<Filter>>(emptyList())
    val filters: StateFlow<List<Filter>> = _filters


    fun addFilter(filter: Filter) {
        if (!_filters.value.contains(filter)) {
            _filters.value = _filters.value.plus(filter)
        }
    }

    fun removeFilterById(id: String) {
        _filters.value = _filters.value.filter { it.id != id }
    }

    fun addFilterById(id: String) {
        if (_filters.value.none { it.id == id }) {
            _filters.value = _filters.value.plus(Filter(id, false))
        }
    }

    fun changeFilterStatus(id: String) {
        _filters.value.map {
            if (it.id == id)
                it.applied = !it.applied
        }
    }

    fun isEnabled(id: String): Boolean {
        return !_filters.value.none { it.id == id && it.applied}
    }
}

data class Filter (
    val id: String,
    var applied: Boolean
)

