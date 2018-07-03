package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {
    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var cur = start
            override fun hasNext(): Boolean = contains(cur)
            override fun next(): MyDate {
                val tmp = cur
                cur = cur.nextDay()
                return tmp
            }
        }
    }

    override fun contains(value: MyDate) = start <= value && value <= endInclusive
}

class RepeatedTimeInterval(val interval: TimeInterval, val times: Int)
operator fun TimeInterval.times(num: Int) = RepeatedTimeInterval(this, num)

operator fun MyDate.plus(ti: TimeInterval) = this.addTimeIntervals(ti, 1)
operator fun MyDate.plus(rti: RepeatedTimeInterval) = this.addTimeIntervals(rti.interval, rti.times)
