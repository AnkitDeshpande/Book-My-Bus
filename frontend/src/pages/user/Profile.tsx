import { useEffect } from 'react'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { toast } from 'sonner'
import { useAppDispatch } from '@/store/hooks'
import { setCredentials } from '@/store/slices/authSlice'
import { useAuthToken } from '@/store/hooks'
import { usersApi } from '@/api/users'
import { Card, CardContent, CardHeader, CardTitle } from '@/components/ui/Card'
import Input from '@/components/ui/Input'
import Button from '@/components/ui/Button'
import { PageSpinner } from '@/components/ui/Spinner'

const schema = z.object({
  firstName: z.string().min(1, 'Required'),
  lastName: z.string().min(1, 'Required'),
  phone: z.string().regex(/^[6-9]\d{9}$/, 'Enter a valid 10-digit mobile number'),
})
type FormData = z.infer<typeof schema>

export default function Profile() {
  const dispatch = useAppDispatch()
  const token = useAuthToken()
  const queryClient = useQueryClient()

  const { data: user, isLoading } = useQuery({
    queryKey: ['me'],
    queryFn: () => usersApi.getMe().then((r) => r.data.data),
  })

  const { register, handleSubmit, reset, formState: { errors } } = useForm<FormData>({
    resolver: zodResolver(schema),
  })

  useEffect(() => {
    if (user) reset({ firstName: user.firstName, lastName: user.lastName, phone: user.phone })
  }, [user, reset])

  const mutation = useMutation({
    mutationFn: (data: FormData) => usersApi.updateMe(data),
    onSuccess: (res) => {
      toast.success('Profile updated')
      dispatch(setCredentials({ token: token!, user: res.data.data }))
      queryClient.setQueryData(['me'], res.data.data)
    },
    onError: () => toast.error('Update failed'),
  })

  if (isLoading) return <PageSpinner />

  return (
    <div className="mx-auto max-w-lg px-4 py-8">
      <h1 className="text-2xl font-bold text-gray-900 mb-6">My Profile</h1>

      <Card className="mb-6">
        <CardContent className="pt-6">
          <div className="flex items-center gap-4">
            <div className="flex size-16 items-center justify-center rounded-full bg-primary-100 text-primary-700 text-2xl font-bold">
              {user?.firstName?.[0]?.toUpperCase()}
            </div>
            <div>
              <p className="font-semibold text-gray-900">{user?.firstName} {user?.lastName}</p>
              <p className="text-sm text-gray-500">{user?.email}</p>
              <p className="text-xs text-gray-400 capitalize">{user?.role?.toLowerCase()}</p>
            </div>
          </div>
        </CardContent>
      </Card>

      <Card>
        <CardHeader><CardTitle>Edit Profile</CardTitle></CardHeader>
        <CardContent>
          <form onSubmit={handleSubmit((d) => mutation.mutate(d))} className="space-y-4">
            <div className="grid grid-cols-2 gap-3">
              <Input label="First Name" error={errors.firstName?.message} {...register('firstName')} />
              <Input label="Last Name" error={errors.lastName?.message} {...register('lastName')} />
            </div>
            <Input label="Email" value={user?.email ?? ''} disabled className="bg-gray-50 text-gray-400" />
            <Input label="Username" value={user?.username ?? ''} disabled className="bg-gray-50 text-gray-400" />
            <Input label="Phone" type="tel" placeholder="10-digit number" error={errors.phone?.message} {...register('phone')} />
            <Button type="submit" loading={mutation.isPending}>Save Changes</Button>
          </form>
        </CardContent>
      </Card>
    </div>
  )
}
