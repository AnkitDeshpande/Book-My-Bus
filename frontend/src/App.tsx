import { createBrowserRouter, RouterProvider } from 'react-router'
import RootLayout from '@/layouts/RootLayout'
import AdminLayout from '@/layouts/AdminLayout'
import ProtectedRoute from '@/components/ProtectedRoute'
import Home from '@/pages/Home'
import Login from '@/pages/auth/Login'
import Register from '@/pages/auth/Register'
import Activate from '@/pages/auth/Activate'
import ForgotPassword from '@/pages/auth/ForgotPassword'
import ResetPassword from '@/pages/auth/ResetPassword'
import SearchBuses from '@/pages/user/SearchBuses'
import MyBookings from '@/pages/user/MyBookings'
import BookingDetail from '@/pages/user/BookingDetail'
import Profile from '@/pages/user/Profile'
import Feedback from '@/pages/user/Feedback'
import AdminDashboard from '@/pages/admin/Dashboard'
import AdminUsers from '@/pages/admin/Users'
import AdminBuses from '@/pages/admin/Buses'
import AdminRoutes from '@/pages/admin/Routes'
import AdminBookings from '@/pages/admin/Bookings'
import AdminRevenue from '@/pages/admin/Revenue'
import NotFound from '@/pages/NotFound'

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      { index: true, element: <Home /> },
      { path: 'login', element: <Login /> },
      { path: 'register', element: <Register /> },
      { path: 'auth/activate', element: <Activate /> },
      { path: 'auth/forgot-password', element: <ForgotPassword /> },
      { path: 'auth/reset-password', element: <ResetPassword /> },
      { path: 'buses/search', element: <SearchBuses /> },
      {
        element: <ProtectedRoute />,
        children: [
          { path: 'bookings', element: <MyBookings /> },
          { path: 'bookings/:id', element: <BookingDetail /> },
          { path: 'profile', element: <Profile /> },
          { path: 'feedback', element: <Feedback /> },
        ],
      },
      { path: '*', element: <NotFound /> },
    ],
  },
  {
    path: '/admin',
    element: <ProtectedRoute requireAdmin />,
    children: [
      {
        element: <AdminLayout />,
        children: [
          { index: true, element: <AdminDashboard /> },
          { path: 'users', element: <AdminUsers /> },
          { path: 'buses', element: <AdminBuses /> },
          { path: 'routes', element: <AdminRoutes /> },
          { path: 'bookings', element: <AdminBookings /> },
          { path: 'revenue', element: <AdminRevenue /> },
        ],
      },
    ],
  },
])

export default function App() {
  return <RouterProvider router={router} />
}
