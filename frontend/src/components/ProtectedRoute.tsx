import { Navigate, Outlet } from 'react-router'
import { useIsAuthenticated, useIsAdmin } from '@/store/hooks'

interface Props {
  requireAdmin?: boolean
}

export default function ProtectedRoute({ requireAdmin = false }: Props) {
  const isAuthenticated = useIsAuthenticated()
  const isAdmin = useIsAdmin()

  if (!isAuthenticated) return <Navigate to="/login" replace />
  if (requireAdmin && !isAdmin) return <Navigate to="/" replace />

  return <Outlet />
}
