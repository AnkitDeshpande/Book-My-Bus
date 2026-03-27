import apiClient from './client'
import type { ApiResponse, FeedbackResponse } from '@/types'

export const feedbackApi = {
  submit: (data: {
    bookingId: number
    overallRating: number
    driverRating: number
    serviceRating: number
    comment?: string
  }) => apiClient.post<ApiResponse<FeedbackResponse>>('/api/v1/feedback', data),

  getMy: () =>
    apiClient.get<ApiResponse<FeedbackResponse[]>>('/api/v1/feedback/my'),

  getByBus: (busId: number) =>
    apiClient.get<ApiResponse<FeedbackResponse[]>>(`/api/v1/feedback/bus/${busId}`),
}
