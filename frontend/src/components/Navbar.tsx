import { useState } from 'react'
import { Link, NavLink, useNavigate } from 'react-router'
import { Bus, Menu, X, User, LogOut, LayoutDashboard, Ticket, Sun, Moon } from 'lucide-react'
import { useAppDispatch, useCurrentUser, useIsAuthenticated, useIsAdmin } from '@/store/hooks'
import { logout } from '@/store/slices/authSlice'
import { useQueryClient } from '@tanstack/react-query'
import { useTheme } from '@/hooks/useTheme'

export default function Navbar() {
  const [open, setOpen] = useState(false)
  const [dropdownOpen, setDropdownOpen] = useState(false)
  const dispatch = useAppDispatch()
  const navigate = useNavigate()
  const queryClient = useQueryClient()
  const isAuthenticated = useIsAuthenticated()
  const isAdmin = useIsAdmin()
  const user = useCurrentUser()
  const { dark, toggle } = useTheme()

  function handleLogout() {
    dispatch(logout())
    queryClient.clear()
    navigate('/')
  }

  return (
    <header className="sticky top-0 z-40 border-b border-gray-200 bg-white shadow-sm dark:bg-gray-900 dark:border-gray-700">
      <div className="mx-auto flex h-16 max-w-7xl items-center justify-between px-4 sm:px-6">
        {/* Logo */}
        <Link to="/" className="flex items-center gap-2 text-primary-600 font-bold text-xl">
          <Bus className="size-6" />
          <span>Book My Bus</span>
        </Link>

        {/* Desktop nav */}
        <nav className="hidden md:flex items-center gap-6">
          <NavLink to="/buses/search" className={({ isActive }) =>
            `text-sm font-medium transition-colors ${isActive ? 'text-primary-600' : 'text-gray-600 hover:text-primary-600 dark:text-gray-300 dark:hover:text-primary-400'}`
          }>
            Search Buses
          </NavLink>
          {isAdmin && (
            <NavLink to="/admin" className={({ isActive }) =>
              `text-sm font-medium transition-colors ${isActive ? 'text-primary-600' : 'text-gray-600 hover:text-primary-600 dark:text-gray-300 dark:hover:text-primary-400'}`
            }>
              Admin
            </NavLink>
          )}
        </nav>

        {/* Desktop auth + theme toggle */}
        <div className="hidden md:flex items-center gap-3">
          <button
            onClick={toggle}
            className="rounded-lg p-2 text-gray-500 hover:bg-gray-100 dark:text-gray-400 dark:hover:bg-gray-800 transition-colors"
            aria-label="Toggle dark mode"
          >
            {dark ? <Sun className="size-5" /> : <Moon className="size-5" />}
          </button>

          {isAuthenticated ? (
            <div className="relative">
              <button
                onClick={() => setDropdownOpen((v) => !v)}
                className="flex items-center gap-2 rounded-lg px-3 py-2 text-sm font-medium text-gray-700 hover:bg-gray-100 dark:text-gray-200 dark:hover:bg-gray-800"
              >
                <div className="flex size-8 items-center justify-center rounded-full bg-primary-100 text-primary-700 font-semibold text-sm">
                  {user?.firstName?.[0]?.toUpperCase()}
                </div>
                <span>{user?.firstName}</span>
              </button>
              {dropdownOpen && (
                <>
                  <div className="fixed inset-0 z-10" onClick={() => setDropdownOpen(false)} />
                  <div className="absolute right-0 z-20 mt-1 w-48 rounded-xl border border-gray-100 bg-white py-1 shadow-lg dark:bg-gray-800 dark:border-gray-700">
                    <Link to="/bookings" onClick={() => setDropdownOpen(false)}
                      className="flex items-center gap-2 px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 dark:text-gray-200 dark:hover:bg-gray-700">
                      <Ticket className="size-4" /> My Bookings
                    </Link>
                    <Link to="/profile" onClick={() => setDropdownOpen(false)}
                      className="flex items-center gap-2 px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 dark:text-gray-200 dark:hover:bg-gray-700">
                      <User className="size-4" /> Profile
                    </Link>
                    {isAdmin && (
                      <Link to="/admin" onClick={() => setDropdownOpen(false)}
                        className="flex items-center gap-2 px-4 py-2 text-sm text-gray-700 hover:bg-gray-50 dark:text-gray-200 dark:hover:bg-gray-700">
                        <LayoutDashboard className="size-4" /> Admin Panel
                      </Link>
                    )}
                    <hr className="my-1 border-gray-100 dark:border-gray-700" />
                    <button onClick={handleLogout}
                      className="flex w-full items-center gap-2 px-4 py-2 text-sm text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20">
                      <LogOut className="size-4" /> Logout
                    </button>
                  </div>
                </>
              )}
            </div>
          ) : (
            <>
              <Link to="/login" className="text-sm font-medium text-gray-600 hover:text-primary-600 dark:text-gray-300 dark:hover:text-primary-400">Login</Link>
              <Link to="/register"
                className="rounded-lg bg-primary-600 px-4 py-2 text-sm font-medium text-white hover:bg-primary-700 transition-colors">
                Sign Up
              </Link>
            </>
          )}
        </div>

        {/* Mobile: theme toggle + hamburger */}
        <div className="md:hidden flex items-center gap-1">
          <button
            onClick={toggle}
            className="rounded-lg p-2 text-gray-500 hover:bg-gray-100 dark:text-gray-400 dark:hover:bg-gray-800"
            aria-label="Toggle dark mode"
          >
            {dark ? <Sun className="size-5" /> : <Moon className="size-5" />}
          </button>
          <button className="p-2 rounded-lg hover:bg-gray-100 dark:hover:bg-gray-800" onClick={() => setOpen((v) => !v)}>
            {open ? <X className="size-5 dark:text-gray-300" /> : <Menu className="size-5 dark:text-gray-300" />}
          </button>
        </div>
      </div>

      {/* Mobile menu */}
      {open && (
        <div className="border-t border-gray-100 bg-white px-4 py-3 md:hidden space-y-1 dark:bg-gray-900 dark:border-gray-700">
          <NavLink to="/buses/search" onClick={() => setOpen(false)}
            className="block rounded-lg px-3 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50 dark:text-gray-200 dark:hover:bg-gray-800">
            Search Buses
          </NavLink>
          {isAuthenticated ? (
            <>
              <NavLink to="/bookings" onClick={() => setOpen(false)}
                className="block rounded-lg px-3 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50 dark:text-gray-200 dark:hover:bg-gray-800">
                My Bookings
              </NavLink>
              <NavLink to="/profile" onClick={() => setOpen(false)}
                className="block rounded-lg px-3 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50 dark:text-gray-200 dark:hover:bg-gray-800">
                Profile
              </NavLink>
              {isAdmin && (
                <NavLink to="/admin" onClick={() => setOpen(false)}
                  className="block rounded-lg px-3 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50 dark:text-gray-200 dark:hover:bg-gray-800">
                  Admin Panel
                </NavLink>
              )}
              <button onClick={() => { handleLogout(); setOpen(false) }}
                className="block w-full text-left rounded-lg px-3 py-2 text-sm font-medium text-red-600 hover:bg-red-50 dark:hover:bg-red-900/20">
                Logout
              </button>
            </>
          ) : (
            <>
              <NavLink to="/login" onClick={() => setOpen(false)}
                className="block rounded-lg px-3 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50 dark:text-gray-200 dark:hover:bg-gray-800">
                Login
              </NavLink>
              <NavLink to="/register" onClick={() => setOpen(false)}
                className="block rounded-lg px-3 py-2 text-sm font-medium text-primary-600 hover:bg-primary-50">
                Sign Up
              </NavLink>
            </>
          )}
        </div>
      )}
    </header>
  )
}
