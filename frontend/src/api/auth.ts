import apiClient from './client'
import type { ApiResponse, LoginResponse, UserResponse } from '@/types'

export const authApi = {
  register: (data: {
    username: string
    email: string
    password: string
    firstName: string
    lastName: string
    phone: string
  }) => apiClient.post<ApiResponse<UserResponse>>('/api/v1/auth/register', data),

  login: (data: { emailOrUsername: string; password: string }) =>
    apiClient.post<ApiResponse<LoginResponse>>('/api/v1/auth/login', data),

  activate: (token: string) =>
    apiClient.get<ApiResponse<void>>('/api/v1/auth/activate', { params: { token } }),

  resendActivation: (email: string) =>
    apiClient.post<ApiResponse<void>>('/api/v1/auth/resend-activation', null, {
      params: { email },
    }),

  forgotPassword: (email: string) =>
    apiClient.post<ApiResponse<void>>('/api/v1/auth/forgot-password', { email }),

  resetPassword: (token: string, newPassword: string) =>
    apiClient.post<ApiResponse<void>>('/api/v1/auth/reset-password', { token, newPassword }),
}
