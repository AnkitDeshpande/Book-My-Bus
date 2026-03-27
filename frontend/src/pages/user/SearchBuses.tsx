import { useState } from 'react'
import { useSearchParams, useNavigate } from 'react-router'
import { useForm } from 'react-hook-form'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { toast } from 'sonner'
import { Search, Bus, Clock, Users, ArrowRight } from 'lucide-react'
import { busesApi } from '@/api/buses'
import { bookingsApi } from '@/api/bookings'
import { useIsAuthenticated } from '@/store/hooks'
import { formatTime, formatCurrency, busTypeLabel } from '@/lib/utils'
import type { BusResponse } from '@/types'
import Button from '@/components/ui/Button'
import Input from '@/components/ui/Input'
import Badge from '@/components/ui/Badge'
import Dialog from '@/components/ui/Dialog'
import { PageSpinner } from '@/components/ui/Spinner'

interface SearchForm { source: string; destination: string; seatCount: number }
interface BookForm { journeyDate: string; passengerName: string; passengerPhone: string }

export default function SearchBuses() {
  const [params, setParams] = useSearchParams()
  const navigate = useNavigate()
  const isAuthenticated = useIsAuthenticated()
  const queryClient = useQueryClient()
  const [selectedBus, setSelectedBus] = useState<BusResponse | null>(null)

  const source = params.get('source') ?? ''
  const destination = params.get('destination') ?? ''
  const seatCount = Number(params.get('seatCount') ?? 1)

  const searchForm = useForm<SearchForm>({
    defaultValues: { source, destination, seatCount },
  })
  const bookForm = useForm<BookForm>()

  const { data, isLoading, isFetching } = useQuery({
    queryKey: ['buses', 'search', source, destination, seatCount],
    queryFn: () => busesApi.search(source, destination, seatCount).then((r) => r.data.data),
    enabled: !!(source && destination && seatCount),
  })

  const bookMutation = useMutation({
    mutationFn: (form: BookForm) =>
      bookingsApi.create({ busId: selectedBus!.id, journeyDate: form.journeyDate, seatCount, passengerName: form.passengerName, passengerPhone: form.passengerPhone }),
    onSuccess: (res) => {
      toast.success('Booking confirmed! Confirmation email sent.')
      setSelectedBus(null)
      queryClient.invalidateQueries({ queryKey: ['bookings'] })
      navigate(`/bookings/${res.data.data.id}`)
    },
    onError: (err: unknown) => {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message ?? 'Booking failed'
      toast.error(msg)
    },
  })

  function onSearch(data: SearchForm) {
    setParams({ source: data.source, destination: data.destination, seatCount: String(data.seatCount) })
  }

  function handleBookClick(bus: BusResponse) {
    if (!isAuthenticated) { navigate('/login'); return }
    setSelectedBus(bus)
    bookForm.reset()
  }

  const tomorrow = new Date(); tomorrow.setDate(tomorrow.getDate() + 1)
  const minDate = tomorrow.toISOString().split('T')[0]

  return (
    <div className="mx-auto max-w-5xl px-4 py-8">
      {/* Search bar */}
      <div className="rounded-2xl border border-gray-200 bg-white p-5 shadow-sm mb-8 dark:bg-gray-800 dark:border-gray-700">
        <form onSubmit={searchForm.handleSubmit(onSearch)} className="flex flex-col sm:flex-row gap-3 items-end">
          <div className="flex-1">
            <Input label="From" placeholder="e.g. Mumbai" {...searchForm.register('source', { required: true })} />
          </div>
          <div className="flex-1">
            <Input label="To" placeholder="e.g. Pune" {...searchForm.register('destination', { required: true })} />
          </div>
          <div className="w-28">
            <Input label="Seats" type="number" min={1} {...searchForm.register('seatCount', { valueAsNumber: true })} />
          </div>
          <Button type="submit" className="gap-2 shrink-0">
            <Search className="size-4" /> Search
          </Button>
        </form>
      </div>

      {/* Results */}
      {isLoading || isFetching ? (
        <PageSpinner />
      ) : !source || !destination ? (
        <div className="text-center py-20 text-gray-400 dark:text-gray-500">Enter source and destination to search buses.</div>
      ) : !data?.length ? (
        <div className="text-center py-20">
          <Bus className="mx-auto size-12 text-gray-300 mb-3" />
          <p className="text-gray-500 dark:text-gray-400">No buses found for this route. Try different cities or fewer seats.</p>
        </div>
      ) : (
        <div className="space-y-4">
          <p className="text-sm text-gray-500 dark:text-gray-400">{data.length} bus{data.length !== 1 ? 'es' : ''} found</p>
          {data.map((bus) => (
            <div key={bus.id} className="rounded-xl border border-gray-200 bg-white p-5 shadow-sm hover:shadow-md transition-shadow dark:bg-gray-800 dark:border-gray-700">
              <div className="flex flex-col sm:flex-row sm:items-center gap-4">
                <div className="flex-1">
                  <div className="flex items-center gap-2 mb-1">
                    <h3 className="font-semibold text-gray-900 dark:text-gray-100">{bus.busName}</h3>
                    <Badge variant="info">{busTypeLabel(bus.busType)}</Badge>
                  </div>
                  <p className="text-xs text-gray-400 dark:text-gray-500 mb-3">{bus.busNumber} · Driver: {bus.driverName}</p>
                  <div className="flex items-center gap-6 text-sm text-gray-600 dark:text-gray-300">
                    <div className="flex items-center gap-1.5">
                      <Clock className="size-4 text-gray-400" />
                      <span className="font-medium">{formatTime(bus.departureTime)}</span>
                      <ArrowRight className="size-3 text-gray-300" />
                      <span className="font-medium">{formatTime(bus.arrivalTime)}</span>
                    </div>
                    <div className="flex items-center gap-1.5">
                      <Users className="size-4 text-gray-400" />
                      <span>{bus.availableSeats} seats left</span>
                    </div>
                  </div>
                  <p className="text-xs text-gray-400 dark:text-gray-500 mt-1">{bus.routeSource} → {bus.routeDestination}</p>
                </div>
                <div className="flex sm:flex-col items-center sm:items-end gap-3">
                  <p className="text-xl font-bold text-primary-600">{formatCurrency(bus.availableSeats)}</p>
                  <Button onClick={() => handleBookClick(bus)}>Book Now</Button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}

      {/* Booking dialog */}
      <Dialog open={!!selectedBus} onClose={() => setSelectedBus(null)} title="Complete Your Booking">
        {selectedBus && (
          <form onSubmit={bookForm.handleSubmit((d) => bookMutation.mutate(d))} className="space-y-4">
            <div className="rounded-lg bg-gray-50 p-3 text-sm dark:bg-gray-700">
              <p className="font-medium dark:text-gray-100">{selectedBus.busName}</p>
              <p className="text-gray-500 dark:text-gray-400">{selectedBus.routeSource} → {selectedBus.routeDestination} · {seatCount} seat{seatCount > 1 ? 's' : ''}</p>
            </div>
            <Input
              label="Journey Date"
              type="date"
              min={minDate}
              error={bookForm.formState.errors.journeyDate?.message}
              {...bookForm.register('journeyDate', { required: 'Journey date is required' })}
            />
            <Input
              label="Passenger Name"
              placeholder="Full name"
              error={bookForm.formState.errors.passengerName?.message}
              {...bookForm.register('passengerName', { required: 'Required' })}
            />
            <Input
              label="Passenger Phone"
              type="tel"
              placeholder="10-digit mobile number"
              error={bookForm.formState.errors.passengerPhone?.message}
              {...bookForm.register('passengerPhone', {
                required: 'Required',
                pattern: { value: /^[6-9]\d{9}$/, message: 'Enter valid 10-digit number' },
              })}
            />
            <div className="flex gap-3 pt-2">
              <Button type="button" variant="outline" onClick={() => setSelectedBus(null)} className="flex-1">Cancel</Button>
              <Button type="submit" className="flex-1" loading={bookMutation.isPending}>Confirm Booking</Button>
            </div>
          </form>
        )}
      </Dialog>
    </div>
  )
}
