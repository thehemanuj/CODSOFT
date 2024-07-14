package ay.building.nudge

import android.app.Application

class NudgeApp: Application() {

    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}