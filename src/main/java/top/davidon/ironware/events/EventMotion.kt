package top.davidon.ironware.events

import com.darkmagician6.eventapi.events.Event
import com.darkmagician6.eventapi.types.EventType

class EventMotion(
    var x: Double,
    var y: Double,
    var z: Double,
    var yaw: Float,
    var pitch: Float,
    var onGround: Boolean,
    var type: EventType
) : Event