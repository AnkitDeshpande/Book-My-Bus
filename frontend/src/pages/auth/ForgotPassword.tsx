import { Link } from 'react-router'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useMutation } from '@tanstack/react-query'
import { toast } from 'sonner'
import { CheckCircle } from 'lucide-react'
import { authApi } from '@/api/auth'
import Input from '@/components/ui/Input'
import Button from '@/components/ui/Button'

const schema = z.object({ email: z.string().email('Invalid email address') })
type FormData = z.infer<typeof schema>

export default function ForgotPassword() {
  const { register, handleSubmit, formState: { errors } } = useForm<FormData>({
    resolver: zodResolver(schema),
  })

  const mutation = useMutation({
    mutationFn: (data: FormData) => authApi.forgotPassword(data.email),
    onError: (err: unknown) => {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message ?? 'Something went wrong'
      toast.error(msg)
    },
  })

  if (mutation.isSuccess) {
    return (
      <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center px-4 dark:bg-gray-950">
        <div className="text-center max-w-sm">
          <CheckCircle className="mx-auto size-16 text-primary-600 mb-4" />
          <h2 className="text-xl font-bold text-gray-900 dark:text-gray-100">Check your email</h2>
          <p className="mt-2 text-sm text-gray-500 dark:text-gray-400">
            We've sent a password reset link. It expires in 1 hour.
          </p>
          <Link to="/login" className="mt-6 inline-block text-sm text-primary-600 hover:underline">
            Back to login
          </Link>
        </div>
      </div>
    )
  }

  return (
    <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center bg-gray-50 px-4 py-12 dark:bg-gray-950">
      <div className="w-full max-w-md">
        <div className="mb-8 text-center">
          <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100">Forgot password?</h1>
          <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">Enter your email and we'll send a reset link</p>
        </div>
        <div className="rounded-2xl border border-gray-200 bg-white p-8 shadow-sm dark:bg-gray-800 dark:border-gray-700">
          <form onSubmit={handleSubmit((d) => mutation.mutate(d))} className="space-y-4">
            <Input label="Email" type="email" placeholder="you@example.com" error={errors.email?.message} {...register('email')} />
            <Button type="submit" className="w-full" size="lg" loading={mutation.isPending}>
              Send Reset Link
            </Button>
          </form>
          <p className="mt-4 text-center text-sm text-gray-500 dark:text-gray-400">
            <Link to="/login" className="text-primary-600 hover:underline">Back to login</Link>
          </p>
        </div>
      </div>
    </div>
  )
}
