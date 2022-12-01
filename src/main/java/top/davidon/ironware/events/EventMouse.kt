package top.davidon.ironware.events

import com.darkmagician6.eventapi.events.Event

class EventMouse(
    private val mouseX: Int,
    private val mouseY: Int,
    private val mouseButton: Int,
    private val type: Type
) : Event {
    enum class Type {
        CLICK, CLICK_MOVE, RELEASED, NO_SCREEN
    }
}