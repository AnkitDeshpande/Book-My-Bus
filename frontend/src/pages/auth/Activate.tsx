import { useEffect, useState } from 'react'
import { Link, useSearchParams } from 'react-router'
import { useMutation } from '@tanstack/react-query'
import { CheckCircle, XCircle, Loader } from 'lucide-react'
import { authApi } from '@/api/auth'
import Button from '@/components/ui/Button'

export default function Activate() {
  const [params] = useSearchParams()
  const token = params.get('token') ?? ''
  const [email, setEmail] = useState('')

  const mutation = useMutation({
    mutationFn: () => authApi.activate(token),
  })

  const resend = useMutation({
    mutationFn: () => authApi.resendActivation(email),
  })

  useEffect(() => {
    if (token) mutation.mutate()
  }, [token]) // eslint-disable-line react-hooks/exhaustive-deps

  if (!token) {
    return (
      <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center px-4 dark:bg-gray-950">
        <div className="text-center max-w-sm">
          <XCircle className="mx-auto size-16 text-red-500 mb-4" />
          <h2 className="text-xl font-bold text-gray-900 dark:text-gray-100">Invalid Link</h2>
          <p className="mt-2 text-gray-500 dark:text-gray-400 text-sm">No activation token found. Please check your email for the correct link.</p>
          <Link to="/login" className="mt-6 inline-block">
            <Button>Go to Login</Button>
          </Link>
        </div>
      </div>
    )
  }

  if (mutation.isPending) {
    return (
      <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center dark:bg-gray-950">
        <div className="text-center">
          <Loader className="mx-auto size-10 animate-spin text-primary-600 mb-4" />
          <p className="text-gray-600 dark:text-gray-300">Activating your account…</p>
        </div>
      </div>
    )
  }

  if (mutation.isSuccess) {
    return (
      <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center px-4 dark:bg-gray-950">
        <div className="text-center max-w-sm">
          <CheckCircle className="mx-auto size-16 text-primary-600 mb-4" />
          <h2 className="text-xl font-bold text-gray-900 dark:text-gray-100">Account Activated!</h2>
          <p className="mt-2 text-gray-500 dark:text-gray-400 text-sm">Your account is now active. You can sign in.</p>
          <Link to="/login" className="mt-6 inline-block">
            <Button size="lg">Sign In</Button>
          </Link>
        </div>
      </div>
    )
  }

  return (
    <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center px-4 dark:bg-gray-950">
      <div className="text-center max-w-sm">
        <XCircle className="mx-auto size-16 text-red-500 mb-4" />
        <h2 className="text-xl font-bold text-gray-900 dark:text-gray-100">Activation Failed</h2>
        <p className="mt-2 text-gray-500 dark:text-gray-400 text-sm">
          Your link may have expired. Enter your email to receive a new one.
        </p>
        <div className="mt-6 flex flex-col gap-3">
          <input
            type="email"
            placeholder="your@email.com"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            className="w-full rounded-lg border border-gray-300 px-3 py-2 text-sm focus:border-primary-500 focus:outline-none focus:ring-2 focus:ring-primary-500/20 dark:bg-gray-800 dark:border-gray-600 dark:text-gray-100 dark:placeholder-gray-500"
          />
          <Button onClick={() => resend.mutate()} loading={resend.isPending} disabled={!email}>
            Resend Activation Email
          </Button>
          {resend.isSuccess && <p className="text-sm text-green-600 dark:text-green-400">Email sent! Check your inbox.</p>}
        </div>
      </div>
    </div>
  )
}
