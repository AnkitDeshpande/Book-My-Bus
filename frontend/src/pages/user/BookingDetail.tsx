import { useParams, useNavigate, Link } from 'react-router'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { toast } from 'sonner'
import { ArrowLeft, ArrowRight } from 'lucide-react'
import { bookingsApi } from '@/api/bookings'
import { feedbackApi } from '@/api/feedback'
import { formatDate, formatTime, formatCurrency } from '@/lib/utils'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/Card'
import Badge from '@/components/ui/Badge'
import Button from '@/components/ui/Button'
import { PageSpinner } from '@/components/ui/Spinner'

export default function BookingDetail() {
  const { id } = useParams<{ id: string }>()
  const navigate = useNavigate()
  const queryClient = useQueryClient()

  const { data: booking, isLoading } = useQuery({
    queryKey: ['bookings', id],
    queryFn: () => bookingsApi.getById(Number(id)).then((r) => r.data.data),
  })

  const { data: feedbacks } = useQuery({
    queryKey: ['feedback', 'my'],
    queryFn: () => feedbackApi.getMy().then((r) => r.data.data),
    enabled: !!booking,
  })

  const hasGivenFeedback = feedbacks?.some((f) => f.bookingId === Number(id))
  const canCancel = booking?.bookingStatus === 'CONFIRMED' &&
    new Date(booking.journeyDate) > new Date()

  const cancelMutation = useMutation({
    mutationFn: () => bookingsApi.cancel(Number(id)),
    onSuccess: () => {
      toast.success('Booking cancelled. Refund initiated.')
      queryClient.invalidateQueries({ queryKey: ['bookings'] })
    },
    onError: (err: unknown) => {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message ?? 'Cancellation failed'
      toast.error(msg)
    },
  })

  if (isLoading) return <PageSpinner />
  if (!booking) return <div className="p-8 text-center text-gray-500 dark:text-gray-400">Booking not found.</div>

  return (
    <div className="mx-auto max-w-2xl px-4 py-8">
      <button onClick={() => navigate('/bookings')} className="mb-6 flex items-center gap-1.5 text-sm font-medium text-gray-500 hover:text-gray-900 dark:text-gray-400 dark:hover:text-gray-100 transition-colors">
        <ArrowLeft className="size-4" /> Back to bookings
      </button>

      <Card>
        <CardHeader>
          <div className="flex items-center justify-between">
            <CardTitle>Booking #{booking.bookingNumber}</CardTitle>
            <Badge variant={booking.bookingStatus === 'CONFIRMED' ? 'success' : 'danger'}>
              {booking.bookingStatus}
            </Badge>
          </div>
        </CardHeader>
        <CardContent className="space-y-4">
          {/* Route */}
          <div className="flex items-center gap-3 rounded-lg bg-gray-50 p-4 dark:bg-gray-700">
            <div className="text-center">
              <p className="text-lg font-bold text-gray-900 dark:text-gray-100">{formatTime(booking.departureTime)}</p>
              <p className="text-sm text-gray-500 dark:text-gray-400">{booking.routeSource}</p>
            </div>
            <div className="flex-1 flex items-center gap-1">
              <div className="h-px flex-1 bg-gray-300 dark:bg-gray-600" />
              <ArrowRight className="size-4 text-gray-400" />
              <div className="h-px flex-1 bg-gray-300 dark:bg-gray-600" />
            </div>
            <div className="text-center">
              <p className="text-lg font-bold text-gray-900 dark:text-gray-100">{formatTime(booking.arrivalTime)}</p>
              <p className="text-sm text-gray-500 dark:text-gray-400">{booking.routeDestination}</p>
            </div>
          </div>

          {/* Details grid */}
          <dl className="grid grid-cols-2 gap-y-3 text-sm">
            {[
              { label: 'Bus', value: `${booking.busName} (${booking.busNumber})` },
              { label: 'Journey Date', value: formatDate(booking.journeyDate) },
              { label: 'Seats', value: booking.seatCount },
              { label: 'Passenger', value: booking.passengerName },
              { label: 'Phone', value: booking.passengerPhone },
              { label: 'Payment', value: booking.paymentStatus },
              { label: 'Total Fare', value: formatCurrency(booking.totalFare), highlight: true },
              { label: 'Booked On', value: formatDate(booking.createdAt) },
            ].map(({ label, value, highlight }) => (
              <div key={label}>
                <dt className="text-gray-400 dark:text-gray-500 text-xs">{label}</dt>
                <dd className={`font-medium ${highlight ? 'text-primary-600 text-base' : 'text-gray-900 dark:text-gray-100'}`}>{value}</dd>
              </div>
            ))}
          </dl>

          {/* Actions */}
          <div className="flex gap-3 pt-2 border-t border-gray-100 dark:border-gray-700">
            {canCancel && (
              <Button
                variant="danger"
                onClick={() => {
                  if (confirm('Cancel this booking?')) cancelMutation.mutate()
                }}
                loading={cancelMutation.isPending}
              >
                Cancel Booking
              </Button>
            )}
            {booking.bookingStatus === 'CONFIRMED' && !hasGivenFeedback && (
              <Link to={`/feedback?bookingId=${booking.id}`}>
                <Button variant="outline">Leave Feedback</Button>
              </Link>
            )}
            {hasGivenFeedback && (
              <span className="text-sm text-gray-400 dark:text-gray-500 self-center">✓ Feedback submitted</span>
            )}
          </div>
        </CardContent>
      </Card>
    </div>
  )
}
