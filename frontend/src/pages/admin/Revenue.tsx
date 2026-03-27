import { useState } from 'react'
import { useQuery } from '@tanstack/react-query'
import { adminApi } from '@/api/admin'
import { formatCurrency, formatDateTime } from '@/lib/utils'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/Card'
import Button from '@/components/ui/Button'
import Input from '@/components/ui/Input'
import { PageSpinner } from '@/components/ui/Spinner'
import { TrendingUp, Ticket, CheckCircle } from 'lucide-react'

export default function AdminRevenue() {
  const now = new Date()
  const firstOfMonth = new Date(now.getFullYear(), now.getMonth(), 1)

  const [from, setFrom] = useState(firstOfMonth.toISOString().slice(0, 16))
  const [to, setTo] = useState(now.toISOString().slice(0, 16))
  const [queryRange, setQueryRange] = useState({ from: `${firstOfMonth.toISOString().slice(0, 16)}:00`, to: `${now.toISOString().slice(0, 16)}:00` })

  const { data, isLoading, isFetching } = useQuery({
    queryKey: ['admin', 'revenue', queryRange.from, queryRange.to],
    queryFn: () => adminApi.getRevenue(queryRange.from, queryRange.to).then((r) => r.data.data),
  })

  function applyFilter() {
    setQueryRange({ from: `${from}:00`, to: `${to}:00` })
  }

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-900 mb-6 dark:text-gray-100">Revenue Report</h1>

      {/* Filter */}
      <div className="rounded-xl border border-gray-200 bg-white p-5 shadow-sm mb-6 dark:bg-gray-800 dark:border-gray-700">
        <div className="flex flex-col sm:flex-row gap-3 items-end">
          <Input label="From" type="datetime-local" value={from} onChange={(e) => setFrom(e.target.value)} />
          <Input label="To" type="datetime-local" value={to} onChange={(e) => setTo(e.target.value)} />
          <Button onClick={applyFilter} loading={isFetching} className="shrink-0">Apply</Button>
        </div>
      </div>

      {isLoading ? <PageSpinner /> : data && (
        <>
          <div className="grid grid-cols-1 gap-4 sm:grid-cols-3 mb-6">
            <Card>
              <CardContent className="pt-6">
                <div className="flex items-center gap-3 mb-2">
                  <div className="flex size-10 items-center justify-center rounded-lg bg-yellow-500">
                    <TrendingUp className="size-5 text-white" />
                  </div>
                  <p className="text-sm text-gray-500 dark:text-gray-400">Total Revenue</p>
                </div>
                <p className="text-3xl font-bold text-gray-900 dark:text-gray-100">{formatCurrency(data.totalRevenue)}</p>
              </CardContent>
            </Card>
            <Card>
              <CardContent className="pt-6">
                <div className="flex items-center gap-3 mb-2">
                  <div className="flex size-10 items-center justify-center rounded-lg bg-blue-500">
                    <Ticket className="size-5 text-white" />
                  </div>
                  <p className="text-sm text-gray-500 dark:text-gray-400">Total Bookings</p>
                </div>
                <p className="text-3xl font-bold text-gray-900 dark:text-gray-100">{data.totalBookings}</p>
              </CardContent>
            </Card>
            <Card>
              <CardContent className="pt-6">
                <div className="flex items-center gap-3 mb-2">
                  <div className="flex size-10 items-center justify-center rounded-lg bg-emerald-500">
                    <CheckCircle className="size-5 text-white" />
                  </div>
                  <p className="text-sm text-gray-500 dark:text-gray-400">Confirmed</p>
                </div>
                <p className="text-3xl font-bold text-gray-900 dark:text-gray-100">{data.confirmedBookings}</p>
              </CardContent>
            </Card>
          </div>

          <Card>
            <CardHeader><CardTitle>Report Summary</CardTitle></CardHeader>
            <CardContent>
              <dl className="grid grid-cols-2 gap-4 text-sm">
                <div><dt className="text-gray-400 dark:text-gray-500">Period From</dt><dd className="font-medium mt-0.5 dark:text-gray-200">{formatDateTime(data.from)}</dd></div>
                <div><dt className="text-gray-400 dark:text-gray-500">Period To</dt><dd className="font-medium mt-0.5 dark:text-gray-200">{formatDateTime(data.to)}</dd></div>
                <div><dt className="text-gray-400 dark:text-gray-500">Avg. Revenue/Booking</dt>
                  <dd className="font-medium mt-0.5 dark:text-gray-200">
                    {data.confirmedBookings > 0 ? formatCurrency(data.totalRevenue / data.confirmedBookings) : '—'}
                  </dd>
                </div>
                <div><dt className="text-gray-400 dark:text-gray-500">Cancellation Rate</dt>
                  <dd className="font-medium mt-0.5 dark:text-gray-200">
                    {data.totalBookings > 0
                      ? `${Math.round(((data.totalBookings - data.confirmedBookings) / data.totalBookings) * 100)}%`
                      : '—'}
                  </dd>
                </div>
              </dl>
            </CardContent>
          </Card>
        </>
      )}
    </div>
  )
}
