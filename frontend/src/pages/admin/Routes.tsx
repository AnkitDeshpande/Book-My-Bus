import { useState } from 'react'
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query'
import { useForm } from 'react-hook-form'
import { zodResolver } from '@hookform/resolvers/zod'
import { z } from 'zod'
import { toast } from 'sonner'
import { Plus, Pencil, Trash2 } from 'lucide-react'
import { routesApi } from '@/api/routes'
import { formatCurrency } from '@/lib/utils'
import type { RouteResponse } from '@/types'
import Button from '@/components/ui/Button'
import Input from '@/components/ui/Input'
import Badge from '@/components/ui/Badge'
import Dialog from '@/components/ui/Dialog'
import { PageSpinner } from '@/components/ui/Spinner'

const schema = z.object({
  source: z.string().min(1, 'Required'),
  destination: z.string().min(1, 'Required'),
  distanceKm: z.number({ invalid_type_error: 'Required' }).min(1),
  baseFare: z.number({ invalid_type_error: 'Required' }).min(1),
})
type FormData = z.infer<typeof schema>

export default function AdminRoutes() {
  const [page] = useState(0)
  const [dialogOpen, setDialogOpen] = useState(false)
  const [editing, setEditing] = useState<RouteResponse | null>(null)
  const queryClient = useQueryClient()

  const { data, isLoading } = useQuery({
    queryKey: ['admin', 'routes', page],
    queryFn: () => routesApi.getAll(page, 50).then((r) => r.data.data),
  })

  const { register, handleSubmit, reset, formState: { errors } } = useForm<FormData>({
    resolver: zodResolver(schema),
  })

  function openCreate() { setEditing(null); reset({}); setDialogOpen(true) }
  function openEdit(r: RouteResponse) {
    setEditing(r)
    reset({ source: r.source, destination: r.destination, distanceKm: r.distanceKm, baseFare: r.baseFare })
    setDialogOpen(true)
  }

  const saveMutation = useMutation({
    mutationFn: (data: FormData) =>
      editing ? routesApi.update(editing.id, data) : routesApi.create(data),
    onSuccess: () => {
      toast.success(editing ? 'Route updated' : 'Route created')
      setDialogOpen(false)
      queryClient.invalidateQueries({ queryKey: ['admin', 'routes'] })
    },
    onError: (err: unknown) => {
      const msg = (err as { response?: { data?: { message?: string } } })?.response?.data?.message ?? 'Failed'
      toast.error(msg)
    },
  })

  const deleteMutation = useMutation({
    mutationFn: (id: number) => routesApi.deactivate(id),
    onSuccess: () => { toast.success('Route deactivated'); queryClient.invalidateQueries({ queryKey: ['admin', 'routes'] }) },
  })

  if (isLoading) return <PageSpinner />

  return (
    <div>
      <div className="flex items-center justify-between mb-6">
        <h1 className="text-2xl font-bold text-gray-900 dark:text-gray-100">Routes</h1>
        <Button onClick={openCreate} className="gap-2"><Plus className="size-4" /> Add Route</Button>
      </div>

      <div className="rounded-xl border border-gray-200 bg-white shadow-sm overflow-hidden dark:bg-gray-800 dark:border-gray-700">
        <table className="w-full text-sm">
          <thead className="bg-gray-50 border-b border-gray-200 dark:bg-gray-700 dark:border-gray-600">
            <tr>
              {['Source', 'Destination', 'Distance', 'Base Fare', 'Status', 'Actions'].map((h) => (
                <th key={h} className="px-4 py-3 text-left text-xs font-semibold text-gray-500 uppercase dark:text-gray-400">{h}</th>
              ))}
            </tr>
          </thead>
          <tbody className="divide-y divide-gray-100 dark:divide-gray-700">
            {data?.content.map((r) => (
              <tr key={r.id} className="hover:bg-gray-50 dark:hover:bg-gray-700">
                <td className="px-4 py-3 font-medium text-gray-900 dark:text-gray-100">{r.source}</td>
                <td className="px-4 py-3 text-gray-700 dark:text-gray-200">{r.destination}</td>
                <td className="px-4 py-3 text-gray-500 dark:text-gray-400">{r.distanceKm} km</td>
                <td className="px-4 py-3 font-medium text-primary-600">{formatCurrency(r.baseFare)}</td>
                <td className="px-4 py-3"><Badge variant={r.active ? 'success' : 'danger'}>{r.active ? 'Active' : 'Inactive'}</Badge></td>
                <td className="px-4 py-3">
                  <div className="flex gap-2">
                    <Button size="sm" variant="outline" onClick={() => openEdit(r)}><Pencil className="size-3.5" /></Button>
                    {r.active && (
                      <Button size="sm" variant="danger" onClick={() => confirm('Deactivate?') && deleteMutation.mutate(r.id)}>
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

      <Dialog open={dialogOpen} onClose={() => setDialogOpen(false)} title={editing ? 'Edit Route' : 'Add Route'}>
        <form onSubmit={handleSubmit((d) => saveMutation.mutate(d))} className="space-y-4">
          <Input label="Source" placeholder="e.g. Mumbai" error={errors.source?.message} {...register('source')} />
          <Input label="Destination" placeholder="e.g. Pune" error={errors.destination?.message} {...register('destination')} />
          <div className="grid grid-cols-2 gap-3">
            <Input label="Distance (km)" type="number" error={errors.distanceKm?.message} {...register('distanceKm', { valueAsNumber: true })} />
            <Input label="Base Fare (₹)" type="number" step="0.01" error={errors.baseFare?.message} {...register('baseFare', { valueAsNumber: true })} />
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
