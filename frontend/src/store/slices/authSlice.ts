import { createSlice, type PayloadAction } from '@reduxjs/toolkit'
import type { UserResponse } from '@/types'
import { loginThunk, fetchMeThunk } from '../thunks/authThunks'

interface AuthState {
  token: string | null
  user: UserResponse | null
  loading: boolean
  error: string | null
}

const initialState: AuthState = {
  token: null,
  user: null,
  loading: false,
  error: null,
}

const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
    logout(state) {
      state.token = null
      state.user = null
      state.error = null
    },
    setCredentials(state, action: PayloadAction<{ token: string; user: UserResponse }>) {
      state.token = action.payload.token
      state.user = action.payload.user
    },
    clearError(state) {
      state.error = null
    },
  },
  extraReducers: (builder) => {
    builder
      .addCase(loginThunk.pending, (state) => {
        state.loading = true
        state.error = null
      })
      .addCase(loginThunk.fulfilled, (state, action) => {
        state.loading = false
        state.token = action.payload.token
        state.user = action.payload.user
      })
      .addCase(loginThunk.rejected, (state, action) => {
        state.loading = false
        state.error = action.payload as string
      })
      .addCase(fetchMeThunk.fulfilled, (state, action) => {
        state.user = action.payload
      })
  },
})

export const { logout, setCredentials, clearError } = authSlice.actions
export default authSlice.reducer
