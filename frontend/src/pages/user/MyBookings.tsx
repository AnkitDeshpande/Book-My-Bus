import { useState } from 'react'
import { Link } from 'react-router'
import { useQuery } from '@tanstack/react-query'
import { Ticket, ArrowRight, ChevronLeft, ChevronRight } from 'lucide-react'
import { bookingsApi } from '@/api/bookings'
import { formatDate, formatTime, formatCurrency } from '@/lib/utils'
import Badge from '@/components/ui/Badge'
import { PageSpinner } from '@/components/ui/Spinner'
import Button from '@/components/ui/Button'

export default function MyBookings() {
  const [page, setPage] = useState(0)

  const { data, isLoading } = useQuery({
    queryKey: ['bookings', 'my', page],
    queryFn: () => bookingsApi.getMy(page, 10).then((r) => r.data.data),
  })

  if (isLoading) return <PageSpinner />

  return (
    <div className="mx-auto max-w-4xl px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-900 mb-6 dark:text-gray-100">My Bookings</h1>

      {!data?.content.length ? (
        <div className="text-center py-20">
          <Ticket className="mx-auto size-12 text-gray-300 mb-3" />
          <p className="text-gray-500 dark:text-gray-400 mb-4">No bookings yet.</p>
          <Link to="/buses/search"><Button>Search Buses</Button></Link>
        </div>
      ) : (
        <>
          <div className="space-y-4">
            {data.content.map((b) => (
              <Link key={b.id} to={`/bookings/${b.id}`}
                className="block rounded-xl border border-gray-200 bg-white p-5 shadow-sm hover:shadow-md transition-shadow dark:bg-gray-800 dark:border-gray-700 dark:hover:bg-gray-750">
                <div className="flex items-start justify-between gap-4">
                  <div>
                    <div className="flex items-center gap-2 mb-1">
                      <span className="font-semibold text-gray-900 dark:text-gray-100">{b.busName}</span>
                      <Badge variant={b.bookingStatus === 'CONFIRMED' ? 'success' : 'danger'}>
                        {b.bookingStatus}
                      </Badge>
                    </div>
                    <div className="flex items-center gap-2 text-sm text-gray-600 mb-1 dark:text-gray-300">
                      <span>{b.routeSource}</span>
                      <ArrowRight className="size-3 text-gray-400" />
                      <span>{b.routeDestination}</span>
                    </div>
                    <div className="text-xs text-gray-400 dark:text-gray-500 space-x-3">
                      <span>📅 {formatDate(b.journeyDate)}</span>
                      <span>🕐 {formatTime(b.departureTime)}</span>
                      <span>🎟 {b.seatCount} seat{b.seatCount > 1 ? 's' : ''}</span>
                      <span>#{b.bookingNumber}</span>
                    </div>
                  </div>
                  <div className="text-right shrink-0">
                    <p className="font-bold text-primary-600">{formatCurrency(b.totalFare)}</p>
                    <p className="text-xs text-gray-400 dark:text-gray-500">{formatDate(b.createdAt)}</p>
                  </div>
                </div>
              </Link>
            ))}
          </div>

          {data.totalPages > 1 && (
            <div className="mt-6 flex items-center justify-center gap-3">
              <Button variant="outline" size="sm" disabled={page === 0} onClick={() => setPage((p) => p - 1)}>
                <ChevronLeft className="size-4" />
              </Button>
              <span className="text-sm text-gray-600 dark:text-gray-300">Page {page + 1} of {data.totalPages}</span>
              <Button variant="outline" size="sm" disabled={data.last} onClick={() => setPage((p) => p + 1)}>
                <ChevronRight className="size-4" />
              </Button>
            </div>
          )}
        </>
      )}
    </div>
  )
}
