import { useDispatch, useSelector } from 'react-redux'
import type { AppDispatch, RootState } from './index'

export const useAppDispatch = () => useDispatch<AppDispatch>()
export const useAppSelector = <T>(selector: (state: RootState) => T): T =>
  useSelector(selector)

// Convenience selectors
export const useAuthToken = () => useAppSelector((s) => s.auth.token)
export const useCurrentUser = () => useAppSelector((s) => s.auth.user)
export const useIsAuthenticated = () => useAppSelector((s) => !!s.auth.token)
export const useIsAdmin = () => useAppSelector((s) => s.auth.user?.role === 'ADMIN')
export const useAuthLoading = () => useAppSelector((s) => s.auth.loading)
export const useAuthError = () => useAppSelector((s) => s.auth.error)
