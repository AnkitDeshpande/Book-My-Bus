import apiClient from './client'
import type { ApiResponse, PagedResponse, RouteResponse } from '@/types'

export const routesApi = {
  getAll: (page = 0, size = 10) =>
    apiClient.get<ApiResponse<PagedResponse<RouteResponse>>>('/api/v1/routes', {
      params: { page, size },
    }),

  getById: (id: number) =>
    apiClient.get<ApiResponse<RouteResponse>>(`/api/v1/routes/${id}`),

  create: (data: { source: string; destination: string; distanceKm: number; baseFare: number }) =>
    apiClient.post<ApiResponse<RouteResponse>>('/api/v1/routes', data),

  update: (id: number, data: { source: string; destination: string; distanceKm: number; baseFare: number }) =>
    apiClient.put<ApiResponse<RouteResponse>>(`/api/v1/routes/${id}`, data),

  deactivate: (id: number) =>
    apiClient.delete<ApiResponse<void>>(`/api/v1/routes/${id}`),
}
