import apiClient from './client'
import type { ApiResponse, BookingResponse, PagedResponse } from '@/types'

export const bookingsApi = {
  create: (data: {
    busId: number
    journeyDate: string
    seatCount: number
    passengerName: string
    passengerPhone: string
  }) => apiClient.post<ApiResponse<BookingResponse>>('/api/v1/bookings', data),

  getMy: (page = 0, size = 10) =>
    apiClient.get<ApiResponse<PagedResponse<BookingResponse>>>('/api/v1/bookings/my', {
      params: { page, size },
    }),

  getById: (id: number) =>
    apiClient.get<ApiResponse<BookingResponse>>(`/api/v1/bookings/${id}`),

  getAll: (page = 0, size = 10) =>
    apiClient.get<ApiResponse<PagedResponse<BookingResponse>>>('/api/v1/bookings', {
      params: { page, size },
    }),

  cancel: (id: number) =>
    apiClient.delete<ApiResponse<BookingResponse>>(`/api/v1/bookings/${id}/cancel`),
}
