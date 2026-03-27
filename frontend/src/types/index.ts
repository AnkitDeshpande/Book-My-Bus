// ─── Enums ────────────────────────────────────────────────────────────────────

export type UserRole = 'USER' | 'ADMIN'
export type BusType = 'AC' | 'NON_AC' | 'SLEEPER' | 'SEMI_SLEEPER'
export type BookingStatus = 'CONFIRMED' | 'CANCELLED'
export type PaymentStatus = 'PAID' | 'REFUNDED'

// ─── API Wrapper ───────────────────────────────────────────────────────────────

export interface ApiResponse<T> {
  success: boolean
  message: string
  data: T
  timestamp: string
}

export interface PagedResponse<T> {
  content: T[]
  page: number
  size: number
  totalElements: number
  totalPages: number
  last: boolean
}

// ─── Auth ──────────────────────────────────────────────────────────────────────

export interface LoginResponse {
  token: string
  tokenType: string
  userId: number
  username: string
  email: string
  role: UserRole
}

export interface UserResponse {
  id: number
  username: string
  email: string
  firstName: string
  lastName: string
  phone: string
  role: UserRole
  active: boolean
  createdAt: string
}

// ─── Routes ───────────────────────────────────────────────────────────────────

export interface RouteResponse {
  id: number
  source: string
  destination: string
  distanceKm: number
  baseFare: number
  active: boolean
  createdAt: string
}

// ─── Buses ────────────────────────────────────────────────────────────────────

export interface BusResponse {
  id: number
  busNumber: string
  busName: string
  driverName: string
  busType: BusType
  totalSeats: number
  availableSeats: number
  departureTime: string
  arrivalTime: string
  active: boolean
  routeId: number
  routeSource: string
  routeDestination: string
  createdAt: string
}

// ─── Bookings ─────────────────────────────────────────────────────────────────

export interface BookingResponse {
  id: number
  bookingNumber: string
  userId: number
  username: string
  busId: number
  busName: string
  busNumber: string
  routeSource: string
  routeDestination: string
  departureTime: string
  arrivalTime: string
  journeyDate: string
  seatCount: number
  totalFare: number
  bookingStatus: BookingStatus
  paymentStatus: PaymentStatus
  passengerName: string
  passengerPhone: string
  createdAt: string
}

// ─── Feedback ─────────────────────────────────────────────────────────────────

export interface FeedbackResponse {
  id: number
  bookingId: number
  bookingNumber: string
  busId: number
  busName: string
  userId: number
  username: string
  overallRating: number
  driverRating: number
  serviceRating: number
  comment: string
  createdAt: string
}

// ─── Admin ────────────────────────────────────────────────────────────────────

export interface DashboardStats {
  totalUsers: number
  totalBuses: number
  totalRoutes: number
  totalBookings: number
  confirmedBookings: number
  cancelledBookings: number
  totalRevenue: number
}

export interface RevenueReport {
  from: string
  to: string
  totalRevenue: number
  totalBookings: number
  confirmedBookings: number
}
