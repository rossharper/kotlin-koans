package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        return when {
            year != other.year -> year - other.year
            month != other.month -> month - other.month
            else -> dayOfMonth - other.dayOfMonth
        }
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(timeInterval: TimeInterval) : MyDate {
    return addTimeIntervals(timeInterval, 1)
}

operator fun MyDate.plus(repeatedTimeInterval: RepeatedTimeInterval) : MyDate {
    return addTimeIntervals(repeatedTimeInterval.timeInterval, repeatedTimeInterval.n)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatedTimeInterval(val timeInterval: TimeInterval, val n: Int)

operator fun TimeInterval.times(factor: Int) : RepeatedTimeInterval = RepeatedTimeInterval(this, factor)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> = DateIterator(this)

    override operator fun contains(d: MyDate): Boolean {
        return start <= d && d <= endInclusive
    }
}

class DateIterator(val dateRange: DateRange) : Iterator<MyDate> {
    var currentDate : MyDate = dateRange.start

    override fun hasNext(): Boolean {
        return currentDate <= dateRange.endInclusive
    }

    override fun next(): MyDate {
        val next = currentDate
        currentDate = currentDate.nextDay()
        return next
    }

}
