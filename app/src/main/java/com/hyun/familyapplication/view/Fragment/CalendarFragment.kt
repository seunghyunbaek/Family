package com.hyun.familyapplication.view.Fragment


import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hyun.familyapplication.R
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.ui.ViewContainer
import com.kizitonwose.calendarviewsample.*
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.activity_my_home.*
import kotlinx.android.synthetic.main.calendar_day_legend.view.*
import kotlinx.android.synthetic.main.example_3_calendar_day.view.*
import kotlinx.android.synthetic.main.example_3_event_item_view.view.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth
import org.threeten.bp.format.DateTimeFormatter
import java.util.*


private val Context.inputMethodManager
    get() = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

data class Event(val id: String, val text: String, val date: LocalDate)

class Example3EventsAdapter(val onClick: (Event) -> Unit) :
    RecyclerView.Adapter<Example3EventsAdapter.Example3EventsViewHolder>() {

    val events = mutableListOf<Event>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Example3EventsViewHolder {
        return Example3EventsViewHolder(parent.inflate(R.layout.example_3_event_item_view))
    }

    override fun onBindViewHolder(viewHolder: Example3EventsViewHolder, position: Int) {
        viewHolder.bind(events[position])
    }

    override fun getItemCount(): Int = events.size

    inner class Example3EventsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            itemView.setOnClickListener {
                onClick(events[adapterPosition])
            }
        }

        fun bind(event: Event) {
            itemView.itemEventText.text = event.text
        }
    }

}
//
//// TODO: Rename parameter arguments, choose names that match
//// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"
//
///**
// * A simple [Fragment] subclass.
// *
// */

class CalendarFragment : Fragment() {

    private val eventsAdapter = Example3EventsAdapter {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.example_3_dialog_delete_confirmation)
            .setPositiveButton(R.string.delete) { _, _ ->
                deleteEvent(it)
            }
            .setNegativeButton(R.string.close, null)
            .show()
    }

    private val inputDialog by lazy {
        val editText = AppCompatEditText(requireContext())
        val layout = FrameLayout(requireContext()).apply {
            // Setting the padding on the EditText only pads the input area
            // not the entire EditText so we wrap it in a FrameLayout.
            val padding = dpToPx(20, requireContext())
            setPadding(padding, padding, padding, padding)
            addView(
                editText, FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.example_3_input_dialog_title))
            .setView(layout)
            .setPositiveButton(R.string.save) { _, _ ->
                saveEvent(editText.text.toString())
                // Prepare EditText for reuse.
                editText.setText("")
            }
            .setNegativeButton(R.string.close, null)
            .create()
            .apply {
                setOnShowListener {
                    // Show the keyboard
                    editText.requestFocus()
                    context.inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                }
                setOnDismissListener {
                    // Hide the keyboard
                    context.inputMethodManager.toggleSoftInput(
                        InputMethodManager.HIDE_IMPLICIT_ONLY,
                        0
                    )
                }
            }
    }

    val titleRes: Int = R.string.example_3_title

    private var selectedDate: LocalDate? = null
    private val today = LocalDate.now()

    //    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("MMMM")
    private val titleSameYearFormatter = DateTimeFormatter.ofPattern("yyyy.MM")
    //    private val titleFormatter = DateTimeFormatter.ofPattern("MMM yyyy")
    private val titleFormatter = DateTimeFormatter.ofPattern("yyyy.MM")
    //    private val selectionFormatter = DateTimeFormatter.ofPattern("d MMM yyyy")
    private val selectionFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    private val events = mutableMapOf<LocalDate, List<Event>>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        exThreeRv.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
        exThreeRv.adapter = eventsAdapter
        exThreeRv.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))

        val daysOfWeek = daysOfWeekFromLocale()
        val currentMonth = YearMonth.now()
        exThreeCalendar.setup(
            currentMonth.minusMonths(10),
            currentMonth.plusMonths(10),
            daysOfWeek.first()
        )
        exThreeCalendar.scrollToMonth(currentMonth)

        if (savedInstanceState == null) {
            exThreeCalendar.post {
                // Show today's events initially.
                selectDate(today)
            }
        }

        class DayViewContainer(view: View) : ViewContainer(view) {
            lateinit var day: CalendarDay // Will be set when this container is bound.
            val textView = view.calendarText
            val dotView = view.calendarDotView

            init {
                view.setOnClickListener {
                    if (day.owner == DayOwner.THIS_MONTH) {
                        selectDate(day.date)
                    }
                }
            }
        }
        exThreeCalendar.dayBinder = object : DayBinder<DayViewContainer> {
            override fun create(view: View) = DayViewContainer(view)
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                val textView = container.textView
                val dotView = container.dotView

                textView.text = day.date.dayOfMonth.toString()

                if (day.owner == DayOwner.THIS_MONTH) {
                    textView.makeVisible()
                    when (day.date) {
                        today -> {
                            textView.setTextColorRes(R.color.example_3_white)
                            textView.setBackgroundResource(R.drawable.today_bg)
                            dotView.makeInVisible()
                        }
                        selectedDate -> {
                            textView.setTextColorRes(R.color.example_3_blue)
                            textView.setBackgroundResource(R.drawable.selected_bg)
                            dotView.makeInVisible()
                        }
                        else -> {
                            textView.setTextColorRes(R.color.example_3_black)
                            textView.background = null
                            dotView.isVisible = events[day.date].orEmpty().isNotEmpty()
                        }
                    }
                } else {
                    textView.makeInVisible()
                    dotView.makeInVisible()
                }
            }
        }

        exThreeCalendar.monthScrollListener = {
            //            requireActivity().homeToolbar.title = if (it.year == today.year) {
//                titleSameYearFormatter.format(it.yearMonth)
//            } else {
//                titleFormatter.format(it.yearMonth)
//            }
            requireActivity().text_family.text = if (it.year == today.year) {
                titleSameYearFormatter.format(it.yearMonth)
            } else {
                titleFormatter.format(it.yearMonth)
            }
            // Select the first day of the month when
            // we scroll to a new month.
            selectDate(it.yearMonth.atDay(1))
        }

        class MonthViewContainer(view: View) : ViewContainer(view) {
            val legendLayout = view.legendLayout
        }

        exThreeCalendar.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                // Setup each header day text if we have not done that already.
                if (container.legendLayout.tag == null) {
                    container.legendLayout.tag = month.yearMonth
                    container.legendLayout.children.map { it as TextView }
                        .forEachIndexed { index, tv ->
                            //                            tv.text = daysOfWeek[index].name.first().toString()
                            tv.setTextColorRes(R.color.example_3_black)
                        }
                }
            }
        }

        exThreeAddButton.setOnClickListener {
            inputDialog.show()
        }
    }

    private fun selectDate(date: LocalDate) {
        if (selectedDate != date) {
            val oldDate = selectedDate
            selectedDate = date
            oldDate?.let { exThreeCalendar.notifyDateChanged(it) }
            exThreeCalendar.notifyDateChanged(date)
            updateAdapterForDate(date)
        }
    }

    private fun saveEvent(text: String) {
        if (text.isBlank()) {
            Toast.makeText(requireContext(), R.string.example_3_empty_input_text, Toast.LENGTH_LONG)
                .show()
        } else {
            selectedDate?.let {
                events[it] =
                    events[it].orEmpty().plus(Event(UUID.randomUUID().toString(), text, it))
                updateAdapterForDate(it)
            }
        }
    }

    private fun deleteEvent(event: Event) {
        val date = event.date
        events[date] = events[date].orEmpty().minus(event)
        updateAdapterForDate(date)
    }

    private fun updateAdapterForDate(date: LocalDate) {
        eventsAdapter.events.clear()
        eventsAdapter.events.addAll(events[date].orEmpty())
        eventsAdapter.notifyDataSetChanged()
        exThreeSelectedDateText.text = selectionFormatter.format(date)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStart() {
        super.onStart()
//        (activity as AppCompatActivity).homeToolbar.setBackgroundColor(
//            requireContext().getColorCompat(
//                R.color.example_3_toolbar_color
//            )
//        )
//        requireActivity().window.statusBarColor =
//            requireContext().getColorCompat(R.color.example_3_statusbar_color)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onStop() {
        super.onStop()
//        (activity as AppCompatActivity).homeToolbar.setBackgroundColor(
//            requireContext().getColorCompat(
//                R.color.colorPrimary
//            )
//        )
//        requireActivity().window.statusBarColor =
//            requireContext().getColorCompat(R.color.colorPrimaryDark)
    }

    override fun onDestroy() {
        requireActivity().text_family.text = "나의 홈"
        super.onDestroy()
    }
}
