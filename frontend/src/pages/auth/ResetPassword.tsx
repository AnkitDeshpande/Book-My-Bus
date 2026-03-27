import { Link, useSearchParams } from 'react-router'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useMutation } from '@tanstack/react-query'
import { toast } from 'sonner'
import { CheckCircle } from 'lucide-react'
import { authApi } from '@/api/auth'
import Input from '@/components/ui/Input'
import Button from '@/components/ui/Button'

const schema = z.object({
  newPassword: z.string().min(8, 'Password must be at least 8 characters'),
  confirm: z.string(),
}).refine((d) => d.newPassword === d.confirm, { message: 'Passwords do not match', path: ['confirm'] })
type FormData = z.infer<typeof schema>

export default function ResetPassword() {
  const [params] = useSearchParams()
  const token = params.get('token') ?? ''

  const { register, handleSubmit, formState: { errors } } = useForm<FormData>({
    resolver: zodResolver(schema),
  })

  const mutation = useMutation({
    mutationFn: (data: FormData) => authApi.resetPassword(token, data.newPassword),
    onError: (err: unknown) => {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message ?? 'Reset failed'
      toast.error(msg)
    },
  })

  if (mutation.isSuccess) {
    return (
      <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center px-4 dark:bg-gray-950">
        <div className="text-center max-w-sm">
          <CheckCircle className="mx-auto size-16 text-primary-600 mb-4" />
          <h2 className="text-xl font-bold text-gray-900 dark:text-gray-100">Password reset!</h2>
          <p className="mt-2 text-sm text-gray-500 dark:text-gray-400">Your password has been updated. You can now sign in.</p>
          <Link to="/login" className="mt-6 inline-block">
            <Button size="lg">Sign In</Button>
          </Link>
        </div>
      </div>
    )
  }

  return (
    <div className="flex min-h-[calc(100vh-4rem)] items-center justify-center bg-gray-50 px-4 py-12 dark:bg-gray-950">
      <div className="w-full max-w-md">
        <div className="mb-8 text-center">
          <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100">Set new password</h1>
          <p className="mt-1 text-sm text-gray-500 dark:text-gray-400">Choose a strong password</p>
        </div>
        <div className="rounded-2xl border border-gray-200 bg-white p-8 shadow-sm dark:bg-gray-800 dark:border-gray-700">
          <form onSubmit={handleSubmit((d) => mutation.mutate(d))} className="space-y-4">
            <Input label="New Password" type="password" placeholder="Min. 8 characters" error={errors.newPassword?.message} {...register('newPassword')} />
            <Input label="Confirm Password" type="password" placeholder="Repeat password" error={errors.confirm?.message} {...register('confirm')} />
            <Button type="submit" className="w-full" size="lg" loading={mutation.isPending}>
              Reset Password
            </Button>
          </form>
        </div>
      </div>
    </div>
  )
}
