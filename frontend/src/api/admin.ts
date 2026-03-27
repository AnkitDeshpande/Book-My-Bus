import apiClient from './client'
import type { ApiResponse, DashboardStats, PagedResponse, RevenueReport, UserResponse } from '@/types'

export const adminApi = {
  getDashboard: () =>
    apiClient.get<ApiResponse<DashboardStats>>('/api/v1/admin/dashboard'),

  getRevenue: (from: string, to: string) =>
    apiClient.get<ApiResponse<RevenueReport>>('/api/v1/admin/revenue', {
      params: { from, to },
    }),

  getUsers: (page = 0, size = 10) =>
    apiClient.get<ApiResponse<PagedResponse<UserResponse>>>('/api/v1/users', {
      params: { page, size },
    }),

  deactivateUser: (id: number) =>
    apiClient.delete<ApiResponse<void>>(`/api/v1/users/${id}`),
}
