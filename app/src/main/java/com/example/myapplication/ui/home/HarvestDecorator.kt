import android.content.Context
import android.graphics.drawable.Drawable
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.example.myapplication.R

class HarvestDecorator(
    private val context: Context,
    private val dates: HashSet<CalendarDay>
) : DayViewDecorator {

    private val drawable: Drawable = context.getDrawable(R.drawable.ic_harvest)!!

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return dates.contains(day)
    }

    override fun decorate(view: DayViewFacade) {
        view.setBackgroundDrawable(drawable)
    }
}
