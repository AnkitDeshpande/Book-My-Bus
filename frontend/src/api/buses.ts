import apiClient from './client'
import type { ApiResponse, BusResponse, PagedResponse } from '@/types'

export const busesApi = {
  search: (source: string, destination: string, seatCount: number) =>
    apiClient.get<ApiResponse<BusResponse[]>>('/api/v1/buses/search', {
      params: { source, destination, seatCount },
    }),

  getAll: (page = 0, size = 10) =>
    apiClient.get<ApiResponse<PagedResponse<BusResponse>>>('/api/v1/buses', {
      params: { page, size },
    }),

  getById: (id: number) =>
    apiClient.get<ApiResponse<BusResponse>>(`/api/v1/buses/${id}`),

  create: (data: {
    busNumber: string
    busName: string
    driverName: string
    busType: string
    totalSeats: number
    departureTime: string
    arrivalTime: string
    routeId: number
  }) => apiClient.post<ApiResponse<BusResponse>>('/api/v1/buses', data),

  update: (id: number, data: {
    busNumber: string
    busName: string
    driverName: string
    busType: string
    totalSeats: number
    departureTime: string
    arrivalTime: string
    routeId: number
  }) => apiClient.put<ApiResponse<BusResponse>>(`/api/v1/buses/${id}`, data),

  deactivate: (id: number) =>
    apiClient.delete<ApiResponse<void>>(`/api/v1/buses/${id}`),
}
