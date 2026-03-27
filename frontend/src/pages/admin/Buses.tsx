import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { toast } from 'sonner'
import { Plus, Pencil, Trash2 } from 'lucide-react'
import { busesApi } from '@/api/buses'
import { routesApi } from '@/api/routes'
import { formatTime, busTypeLabel } from '@/lib/utils'
import type { BusResponse } from '@/types'
import Button from '@/components/ui/Button'
import Input from '@/components/ui/Input'
import Select from '@/components/ui/Select'
import Badge from '@/components/ui/Badge'
import Dialog from '@/components/ui/Dialog'
import { PageSpinner } from '@/components/ui/Spinner'

const BUS_TYPES = ['AC', 'NON_AC', 'SLEEPER', 'SEMI_SLEEPER']

const schema = z.object({
  busNumber: z.string().min(1, 'Required'),
  busName: z.string().min(1, 'Required'),
  driverName: z.string().min(1, 'Required'),
  busType: z.string().min(1, 'Required'),
  totalSeats: z.number({ invalid_type_error: 'Required' }).min(1),
  departureTime: z.string().min(1, 'Required'),
  arrivalTime: z.string().min(1, 'Required'),
  routeId: z.number({ invalid_type_error: 'Required' }),
})
type FormData = z.infer<typeof schema>

export default function AdminBuses() {
  const [page] = useState(0)
  const [dialogOpen, setDialogOpen] = useState(false)
  const [editing, setEditing] = useState<BusResponse | null>(null)
  const queryClient = useQueryClient()

  const { data, isLoading } = useQuery({
    queryKey: ['admin', 'buses', page],
    queryFn: () => busesApi.getAll(page, 50).then((r) => r.data.data),
  })

  const { data: routes } = useQuery({
    queryKey: ['admin', 'routes', 'all'],
    queryFn: () => routesApi.getAll(0, 100).then((r) => r.data.data),
  })

  const { register, handleSubmit, reset, formState: { errors } } = useForm<FormData>({
    resolver: zodResolver(schema),
  })

  function openCreate() { setEditing(null); reset({}); setDialogOpen(true) }
  function openEdit(b: BusResponse) {
    setEditing(b)
    reset({
      busNumber: b.busNumber, busName: b.busName, driverName: b.driverName,
      busType: b.busType, totalSeats: b.totalSeats,
      departureTime: b.departureTime.slice(0, 5), arrivalTime: b.arrivalTime.slice(0, 5),
      routeId: b.routeId,
    })
    setDialogOpen(true)
  }

  const saveMutation = useMutation({
    mutationFn: (data: FormData) => {
      const payload = { ...data, departureTime: `${data.departureTime}:00`, arrivalTime: `${data.arrivalTime}:00` }
      return editing ? busesApi.update(editing.id, payload) : busesApi.create(payload)
    },
    onSuccess: () => {
      toast.success(editing ? 'Bus updated' : 'Bus created')
      setDialogOpen(false)
      queryClient.invalidateQueries({ queryKey: ['admin', 'buses'] })
    },
    onError: (err: unknown) => {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message ?? 'Failed'
      toast.error(msg)
    },
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => busesApi.deactivate(id),
    onSuccess: () => { toast.success('Bus deactivated'); queryClient.invalidateQueries({ queryKey: ['admin', 'buses'] }) },
  })

  const routeOptions = routes?.content.filter((r) => r.active).map((r) => ({
    value: r.id, label: `${r.source} → ${r.destination}`,
  })) ?? []

  if (isLoading) return <PageSpinner />

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100">Buses</h1>
        <Button onClick={openCreate} className="gap-2"><Plus className="size-4" /> Add Bus</Button>
      </div>

      <div className="rounded-xl border border-gray-200 bg-white shadow-sm overflow-x-auto dark:bg-gray-800 dark:border-gray-700">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 border-b border-gray-200 dark:bg-gray-700 dark:border-gray-600">
            <tr>
              {['Bus', 'Number', 'Type', 'Route', 'Times', 'Seats', 'Status', 'Actions'].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-gray-500 uppercase dark:text-gray-400">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100 dark:divide-gray-700">
            {data?.content.map((b) => (
              <tr key={b.id} className="hover:bg-gray-50 dark:hover:bg-gray-700">
                <td className="px-4 py-3 font-medium text-gray-900 dark:text-gray-100">{b.busName}</td>
                <td className="px-4 py-3 text-gray-500 dark:text-gray-400">{b.busNumber}</td>
                <td className="px-4 py-3"><Badge variant="info">{busTypeLabel(b.busType)}</Badge></td>
                <td className="px-4 py-3 text-gray-600 dark:text-gray-300">{b.routeSource} → {b.routeDestination}</td>
                <td className="px-4 py-3 text-gray-500 dark:text-gray-400">{formatTime(b.departureTime)} – {formatTime(b.arrivalTime)}</td>
                <td className="px-4 py-3 text-gray-600 dark:text-gray-300">{b.availableSeats}/{b.totalSeats}</td>
                <td className="px-4 py-3"><Badge variant={b.active ? 'success' : 'danger'}>{b.active ? 'Active' : 'Inactive'}</Badge></td>
                <td className="px-4 py-3">
                  <div className="flex gap-2">
                    <Button size="sm" variant="outline" onClick={() => openEdit(b)}><Pencil className="size-3.5" /></Button>
                    {b.active && (
                      <Button size="sm" variant="danger" onClick={() => confirm('Deactivate?') && deleteMutation.mutate(b.id)}>
                        <Trash2 className="size-3.5" />
                      </Button>
                    )}
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>

      <Dialog open={dialogOpen} onClose={() => setDialogOpen(false)} title={editing ? 'Edit Bus' : 'Add Bus'} className="max-w-xl">
        <form onSubmit={handleSubmit((d) => saveMutation.mutate(d))} className="space-y-3">
          <div className="grid grid-cols-2 gap-3">
            <Input label="Bus Number" placeholder="MH-01-AB-1234" error={errors.busNumber?.message} {...register('busNumber')} />
            <Input label="Bus Name" placeholder="Shivneri Express" error={errors.busName?.message} {...register('busName')} />
          </div>
          <div className="grid grid-cols-2 gap-3">
            <Input label="Driver Name" placeholder="Driver name" error={errors.driverName?.message} {...register('driverName')} />
            <Input label="Total Seats" type="number" min={1} error={errors.totalSeats?.message} {...register('totalSeats', { valueAsNumber: true })} />
          </div>
          <Select
            label="Bus Type"
            error={errors.busType?.message}
            placeholder="Select type"
            options={BUS_TYPES.map((t) => ({ value: t, label: busTypeLabel(t) }))}
            {...register('busType')}
          />
          <Select
            label="Route"
            error={errors.routeId?.message}
            placeholder="Select route"
            options={routeOptions}
            {...register('routeId', { valueAsNumber: true })}
          />
          <div className="grid grid-cols-2 gap-3">
            <Input label="Departure Time" type="time" error={errors.departureTime?.message} {...register('departureTime')} />
            <Input label="Arrival Time" type="time" error={errors.arrivalTime?.message} {...register('arrivalTime')} />
          </div>
          <div className="flex gap-3 pt-2">
            <Button type="button" variant="outline" onClick={() => setDialogOpen(false)} className="flex-1">Cancel</Button>
            <Button type="submit" className="flex-1" loading={saveMutation.isPending}>{editing ? 'Update' : 'Create'}</Button>
          </div>
        </form>
      </Dialog>
    </div>
  )
}
