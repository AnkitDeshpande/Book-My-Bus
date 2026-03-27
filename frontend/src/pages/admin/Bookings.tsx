import { useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import { ChevronLeft, ChevronRight } from 'lucide-react'
import { bookingsApi } from '@/api/bookings'
import { formatDate, formatCurrency } from '@/lib/utils'
import Badge from '@/components/ui/Badge'
import Button from '@/components/ui/Button'
import { PageSpinner } from '@/components/ui/Spinner'

export default function AdminBookings() {
  const [page, setPage] = useState(0)

  const { data, isLoading } = useQuery({
    queryKey: ['admin', 'bookings', page],
    queryFn: () => bookingsApi.getAll(page, 15).then((r) => r.data.data),
  })

  if (isLoading) return <PageSpinner />

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-900 mb-6 dark:text-gray-100">All Bookings</h1>

      <div className="rounded-xl border border-gray-200 bg-white shadow-sm overflow-x-auto dark:bg-gray-800 dark:border-gray-700">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 border-b border-gray-200 dark:bg-gray-700 dark:border-gray-600">
            <tr>
              {['Booking #', 'User', 'Bus', 'Route', 'Journey Date', 'Seats', 'Fare', 'Status', 'Booked On'].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-gray-500 uppercase dark:text-gray-400">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100 dark:divide-gray-700">
            {data?.content.map((b) => (
              <tr key={b.id} className="hover:bg-gray-50 dark:hover:bg-gray-700">
                <td className="px-4 py-3 font-mono text-xs text-gray-600 dark:text-gray-300">{b.bookingNumber}</td>
                <td className="px-4 py-3 text-gray-700 dark:text-gray-200">{b.username}</td>
                <td className="px-4 py-3 font-medium text-gray-900 dark:text-gray-100">{b.busName}</td>
                <td className="px-4 py-3 text-gray-500 dark:text-gray-400">{b.routeSource} → {b.routeDestination}</td>
                <td className="px-4 py-3 text-gray-600 dark:text-gray-300">{formatDate(b.journeyDate)}</td>
                <td className="px-4 py-3 text-gray-600 dark:text-gray-300">{b.seatCount}</td>
                <td className="px-4 py-3 font-medium text-primary-600">{formatCurrency(b.totalFare)}</td>
                <td className="px-4 py-3">
                  <Badge variant={b.bookingStatus === 'CONFIRMED' ? 'success' : 'danger'}>{b.bookingStatus}</Badge>
                </td>
                <td className="px-4 py-3 text-gray-400 dark:text-gray-500">{formatDate(b.createdAt)}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      {data && data.totalPages > 1 && (
        <div className="mt-4 flex items-center justify-center gap-3">
          <Button variant="outline" size="sm" disabled={page === 0} onClick={() => setPage((p) => p - 1)}>
            <ChevronLeft className="size-4" />
          </Button>
          <span className="text-sm text-gray-600 dark:text-gray-300">Page {page + 1} of {data.totalPages}</span>
          <Button variant="outline" size="sm" disabled={data.last} onClick={() => setPage((p) => p + 1)}>
            <ChevronRight className="size-4" />
          </Button>
        </div>
      )}
    </div>
  )
}
