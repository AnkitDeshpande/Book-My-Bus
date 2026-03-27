import { useQuery } from '@tanstack/react-query'
import { Users, Bus, MapPin, Ticket, TrendingUp, XCircle } from 'lucide-react'
import { adminApi } from '@/api/admin'
import { formatCurrency } from '@/lib/utils'
import { PageSpinner } from '@/components/ui/Spinner'

interface StatCardProps {
  label: string
  value: string | number
  icon: React.ElementType
  color: string
}

function StatCard({ label, value, icon: Icon, color }: StatCardProps) {
  return (
    <div className="rounded-xl border border-gray-200 bg-white p-5 shadow-sm dark:bg-gray-800 dark:border-gray-700">
      <div className="flex items-center justify-between mb-3">
        <p className="text-sm font-medium text-gray-500 dark:text-gray-400">{label}</p>
        <div className={`flex size-10 items-center justify-center rounded-lg ${color}`}>
          <Icon className="size-5 text-white" />
        </div>
      </div>
      <p className="text-2xl font-bold text-gray-900 dark:text-gray-100">{value}</p>
    </div>
  )
}

export default function AdminDashboard() {
  const { data: stats, isLoading } = useQuery({
    queryKey: ['admin', 'dashboard'],
    queryFn: () => adminApi.getDashboard().then((r) => r.data.data),
  })

  if (isLoading) return <PageSpinner />

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-900 mb-6 dark:text-gray-100">Dashboard</h1>

      <div className="grid grid-cols-1 gap-4 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
        <StatCard label="Total Users" value={stats?.totalUsers ?? 0} icon={Users} color="bg-blue-500" />
        <StatCard label="Total Buses" value={stats?.totalBuses ?? 0} icon={Bus} color="bg-primary-600" />
        <StatCard label="Total Routes" value={stats?.totalRoutes ?? 0} icon={MapPin} color="bg-purple-500" />
        <StatCard label="Total Bookings" value={stats?.totalBookings ?? 0} icon={Ticket} color="bg-orange-500" />
        <StatCard label="Confirmed Bookings" value={stats?.confirmedBookings ?? 0} icon={TrendingUp} color="bg-emerald-500" />
        <StatCard label="Cancelled Bookings" value={stats?.cancelledBookings ?? 0} icon={XCircle} color="bg-red-500" />
        <StatCard
          label="Revenue (This Month)"
          value={stats ? formatCurrency(stats.totalRevenue) : '₹0'}
          icon={TrendingUp}
          color="bg-yellow-500"
        />
      </div>

      <div className="mt-8 grid grid-cols-1 gap-6 lg:grid-cols-2">
        <div className="rounded-xl border border-gray-200 bg-white p-5 shadow-sm dark:bg-gray-800 dark:border-gray-700">
          <h3 className="font-semibold text-gray-900 mb-4 dark:text-gray-100">Booking Summary</h3>
          <div className="space-y-3">
            {[
              { label: 'Confirmed', value: stats?.confirmedBookings ?? 0, color: 'bg-emerald-500' },
              { label: 'Cancelled', value: stats?.cancelledBookings ?? 0, color: 'bg-red-500' },
            ].map(({ label, value, color }) => {
              const total = stats?.totalBookings ?? 0
              const pct = total > 0 ? Math.round((value / total) * 100) : 0
              return (
                <div key={label}>
                  <div className="flex justify-between text-sm mb-1">
                    <span className="text-gray-600 dark:text-gray-300">{label}</span>
                    <span className="font-medium dark:text-gray-200">{value} ({pct}%)</span>
                  </div>
                  <div className="h-2 rounded-full bg-gray-100 dark:bg-gray-700">
                    <div className={`h-2 rounded-full ${color}`} style={{ width: `${pct}%` }} />
                  </div>
                </div>
              )
            })}
          </div>
        </div>

        <div className="rounded-xl border border-gray-200 bg-white p-5 shadow-sm dark:bg-gray-800 dark:border-gray-700">
          <h3 className="font-semibold text-gray-900 mb-4 dark:text-gray-100">Quick Actions</h3>
          <div className="space-y-2">
            {[
              { label: 'Manage Buses', href: '/admin/buses' },
              { label: 'Manage Routes', href: '/admin/routes' },
              { label: 'View All Bookings', href: '/admin/bookings' },
              { label: 'Revenue Report', href: '/admin/revenue' },
            ].map(({ label, href }) => (
              <a key={href} href={href}
                className="flex items-center justify-between rounded-lg px-4 py-2.5 text-sm font-medium text-gray-700 hover:bg-gray-50 transition-colors border border-gray-100 dark:text-gray-200 dark:border-gray-700 dark:hover:bg-gray-700">
                {label}
                <span className="text-gray-400 dark:text-gray-500">→</span>
              </a>
            ))}
          </div>
        </div>
      </div>
    </div>
  )
}
