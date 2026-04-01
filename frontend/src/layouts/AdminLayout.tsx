import { NavLink, Outlet, useNavigate, Link } from 'react-router'
import {
  LayoutDashboard, Users, Bus, MapPin, Ticket, TrendingUp, LogOut, ChevronRight,
} from 'lucide-react'
import { useAppDispatch, useCurrentUser } from '@/store/hooks'
import { logout } from '@/store/slices/authSlice'
import { useQueryClient } from '@tanstack/react-query'

const navItems = [
  { to: '/admin', label: 'Dashboard', icon: LayoutDashboard, end: true },
  { to: '/admin/users', label: 'Users', icon: Users },
  { to: '/admin/buses', label: 'Buses', icon: Bus },
  { to: '/admin/routes', label: 'Routes', icon: MapPin },
  { to: '/admin/bookings', label: 'Bookings', icon: Ticket },
  { to: '/admin/revenue', label: 'Revenue', icon: TrendingUp },
]

export default function AdminLayout() {
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const queryClient = useQueryClient()
  const user = useCurrentUser()

  function handleLogout() {
    dispatch(logout())
    queryClient.clear()
    navigate('/')
  }

  return (
    <div className="flex min-h-screen bg-gray-100 dark:bg-gray-950">
      {/* Sidebar */}
      <aside className="flex w-64 flex-shrink-0 flex-col bg-gray-900 text-white">
        <div className="flex h-16 items-center gap-2 px-6 border-b border-gray-700">
          <Bus className="size-6 text-primary-400" />
          <span className="font-bold text-white">Book My Bus</span>
        </div>

        {/* Panel switcher */}
        <div className="px-3 py-3 border-b border-gray-700">
          <div className="flex rounded-lg bg-gray-800 p-1 gap-1">
            <span className="flex-1 text-center rounded-md bg-primary-600 px-2 py-1.5 text-xs font-semibold text-white cursor-default">
              Admin Panel
            </span>
            <Link
              to="/"
              className="flex-1 text-center rounded-md px-2 py-1.5 text-xs font-medium text-gray-400 hover:text-white hover:bg-gray-700 transition-colors"
            >
              User View
            </Link>
          </div>
        </div>

        <nav className="flex-1 px-3 py-4 space-y-1">
          {navItems.map(({ to, label, icon: Icon, end }) => (
            <NavLink
              key={to}
              to={to}
              end={end}
              className={({ isActive }) =>
                `flex items-center gap-3 rounded-lg px-3 py-2.5 text-sm font-medium transition-colors ${
                  isActive
                    ? 'bg-primary-600 text-white'
                    : 'text-gray-400 hover:bg-gray-800 hover:text-white'
                }`
              }
            >
              <Icon className="size-5" />
              {label}
              <ChevronRight className="ml-auto size-4 opacity-40" />
            </NavLink>
          ))}

        </nav>

        <div className="border-t border-gray-700 p-4">
          <div className="mb-3 flex items-center gap-3">
            <div className="flex size-9 items-center justify-center rounded-full bg-primary-600 text-white font-semibold text-sm">
              {user?.firstName?.[0]?.toUpperCase()}
            </div>
            <div className="min-w-0">
              <p className="truncate text-sm font-medium text-white">{user?.firstName} {user?.lastName}</p>
              <p className="truncate text-xs text-gray-400">{user?.email}</p>
            </div>
          </div>
          <button
            onClick={handleLogout}
            className="flex w-full items-center gap-2 rounded-lg px-3 py-2 text-sm text-gray-400 hover:bg-gray-800 hover:text-white transition-colors"
          >
            <LogOut className="size-4" /> Logout
          </button>
        </div>
      </aside>

      {/* Content */}
      <div className="flex-1 flex flex-col overflow-hidden">
        <main className="flex-1 overflow-y-auto p-6">
          <Outlet />
        </main>
      </div>
    </div>
  )
}
