import apiClient from './client'
import type { ApiResponse, UserResponse } from '@/types'

export const usersApi = {
  getMe: (token?: string) =>
    apiClient.get<ApiResponse<UserResponse>>('/api/v1/users/me', {
      headers: token ? { Authorization: `Bearer ${token}` } : undefined,
    }),

  updateMe: (data: { firstName: string; lastName: string; phone: string }) =>
    apiClient.put<ApiResponse<UserResponse>>('/api/v1/users/me', data),
}
