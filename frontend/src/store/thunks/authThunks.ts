import { createAsyncThunk } from '@reduxjs/toolkit'
import { authApi } from '@/api/auth'
import { usersApi } from '@/api/users'
import type { UserResponse } from '@/types'


export const loginThunk = createAsyncThunk(
  'auth/login',
  async (
    credentials: { emailOrUsername: string; password: string },
    { rejectWithValue },
  ) => {
    try {
      const res = await authApi.login(credentials)
      const { token } = res.data.data

      // Fetch full user profile — pass token directly since store isn't updated yet
      const meRes = await usersApi.getMe(token)
      const user = meRes.data.data

      return { token, user }
    } catch (err: unknown) {
      const msg =
        (err as { response?: { data?: { message?: string } } })?.response?.data?.message ??
        'Login failed'
      return rejectWithValue(msg)
    }
  },
)

export const fetchMeThunk = createAsyncThunk<UserResponse>(
  'auth/fetchMe',
  async (_, { rejectWithValue }) => {
    try {
      const res = await usersApi.getMe()
      return res.data.data
    } catch {
      return rejectWithValue('Failed to fetch profile')
    }
  },
)
